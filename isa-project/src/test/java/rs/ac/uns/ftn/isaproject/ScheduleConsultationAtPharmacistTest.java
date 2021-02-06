package rs.ac.uns.ftn.isaproject;

import static org.junit.Assert.assertThrows;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleConsultationAtPharmacistTest {

	private AppointmentService appointmentService;

	@Autowired
	public ScheduleConsultationAtPharmacistTest(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	
	/*Da bi se vidjeli rezultati ovog testa, potrebno je u klasi AppointmentRepository za metodu getCreatedAndScheduledDoctorAppointments
	 * podesiti timeout period na 0s jer ce tada druga nit, bez ikakvog cekanja, pokusati da dobije resurs koji je prva nit zakljucala
	 * i desice se PessimisticLockingFailureException. U realnosti, zelim da omogucim i drugoj niti da pokusa obaviti svoj posao, pa
	 * je stavljam na cekanje od maksimalno 2s.U slucaju da je prvoj niti potrebno vise vremena da zavrsi posao, 
	 * desice se LockTimeoutException koji je obradjen u kontroleru.*/
	
	@Test
	public void testPessimisticLockingScenario() throws Throwable  {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 1");
		     
		        assertThrows(ExecutionException.class, () -> {
		        	LocalDate date = LocalDate.now();
		        	LocalTime startTime =  LocalTime.now();
		        	LocalTime endTime =  LocalTime.now().plusMinutes(30);
					appointmentService.checkDoctorAvailabilityAndAddAppointment(1, date,startTime, endTime,
					new AddAppointmentDTO(1, LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(30).toString(), 1, 1, 800, 3), AppointmentStatus.Scheduled);
				});
			}
		});
		
		Future<?> future2 = executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");
		        
		        assertThrows(PessimisticLockingFailureException.class, () -> {
		        	Thread.sleep(100); 
		        	LocalDate date = LocalDate.now();
		        	LocalTime startTime =  LocalTime.now();
		        	LocalTime endTime =  LocalTime.now().plusMinutes(30);
					appointmentService.checkDoctorAvailabilityAndAddAppointment(1, date,startTime, endTime,
					new AddAppointmentDTO(1, LocalDateTime.now().toString(), LocalDateTime.now().plusMinutes(30).toString(), 1, 1, 800, 3), AppointmentStatus.Scheduled);
				});
		        
			}
		});
			
		future2.get();
	
		executor.shutdown();
	}
}

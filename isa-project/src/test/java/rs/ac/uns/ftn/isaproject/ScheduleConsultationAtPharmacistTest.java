package rs.ac.uns.ftn.isaproject;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	
	@Test
	public void testPessimisticLockingScenario() throws Throwable  {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 1");
		        appointmentService.getUnavailableDoctorAppointments(1);
		        appointmentService.add(new AddAppointmentDTO(1, LocalDateTime.now().toString(), LocalDateTime.now().toString(),
						1, 1, 500, 3), AppointmentStatus.Scheduled);
			}
		});
		
		Future<?> future2 = executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");
		        try { Thread.sleep(50); } catch (InterruptedException e) { }
		        appointmentService.getUnavailableDoctorAppointments(1); // baca PessimisticLockingFailureException
		        appointmentService.add(new AddAppointmentDTO(1, LocalDateTime.now().toString(), LocalDateTime.now().toString(),
		        						1, 1, 500, 3), AppointmentStatus.Scheduled);
			}
		});
		
		ExecutionException thrown = assertThrows(ExecutionException.class, () -> {
			future2.get();
		});
		
		System.out.println("Exception from thread " + thrown.getCause().getClass()); 
		
		assertTrue(thrown.getMessage().contains("PessimisticLockingFailureException"));
		
		executor.shutdown();
	}
}

package rs.ac.uns.ftn.isaproject;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.*;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchedulePredefinedAppointmentAtDermatologistTest {
	
	private AppointmentService appointmentService;

	@Autowired
	public SchedulePredefinedAppointmentAtDermatologistTest(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	
	@Test
	public void testOptimisticLockingScenario() {	
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<?> future1 = executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 1");
				Appointment appointmentToSchedule = appointmentService.getOne(2);
				appointmentToSchedule.setStatus(AppointmentStatus.Scheduled);
				try { Thread.sleep(3000); } catch (InterruptedException e) {}
				appointmentService.save(appointmentToSchedule);// baca ObjectOptimisticLockingFailureException
				
			}
		});
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");
		        Appointment appointmentToSchedule = appointmentService.getOne(2);
				appointmentToSchedule.setStatus(AppointmentStatus.Scheduled);
				appointmentService.save(appointmentToSchedule);
			}
		});
		
		ExecutionException thrown = assertThrows(ExecutionException.class, () -> {
			future1.get();
		});
		
		System.out.println("Exception from thread " + thrown.getCause().getClass()); 
		
		assertTrue(thrown.getMessage().contains("ObjectOptimisticLockingFailureException"));
		
		executor.shutdown();
	}
}

package rs.ac.uns.ftn.isaproject;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugQuantityPharmacyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReduceDrugQuantityTest {

	private DrugQuantityPharmacyService drugQuantityPharmacyService;
	
	@Autowired
	public ReduceDrugQuantityTest(DrugQuantityPharmacyService drugQuantityPharmacyService) {
		this.drugQuantityPharmacyService = drugQuantityPharmacyService;
	}
	
	@Test
	public void testOptimisticLockingScenario() {	
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<?> future1 = executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 1");
		        Collection<DrugQuantityPharmacy> pharmacyQuantities = drugQuantityPharmacyService.findByPharmacyId(1);
				for(DrugQuantityPharmacy drugQuantityPharmacy:pharmacyQuantities) {
					if(drugQuantityPharmacy.getQuantity() > 0)
						drugQuantityPharmacy.setQuantity(drugQuantityPharmacy.getQuantity()-1);
				}
				try { Thread.sleep(3000); } catch (InterruptedException e) {}
				drugQuantityPharmacyService.saveAll(pharmacyQuantities);// baca ObjectOptimisticLockingFailureException
				
			}
		});
		
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");
		        Collection<DrugQuantityPharmacy> pharmacyQuantities = drugQuantityPharmacyService.findByPharmacyId(1);
				for(DrugQuantityPharmacy drugQuantityPharmacy:pharmacyQuantities) {
					if(drugQuantityPharmacy.getQuantity() > 0)
						drugQuantityPharmacy.setQuantity(drugQuantityPharmacy.getQuantity()-1);
				}
				drugQuantityPharmacyService.saveAll(pharmacyQuantities);
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

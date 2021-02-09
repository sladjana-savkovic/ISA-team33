package rs.ac.uns.ftn.isaproject.repository.pharmacy;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{

	@Query("select s from Subscription s where s.pharmacy.id = ?1")
	Collection<Subscription> findByPharmacyId(int id);
	
	@Query(value = "select * from subscription s where s.pharmacy_id = ?1 and s.patient_id = ?2 and is_canceled = false limit 1",
			nativeQuery = true)
	Subscription findByPharmacyIdAndPatientId(int pharmacyId, int patientId);
}

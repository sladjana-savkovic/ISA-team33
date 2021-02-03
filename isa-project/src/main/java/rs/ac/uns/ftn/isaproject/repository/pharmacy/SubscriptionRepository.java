package rs.ac.uns.ftn.isaproject.repository.pharmacy;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>{

	@Query("select s from Subscription s where s.pharmacy.id = ?1")
	Collection<Subscription> findByPharmacyId(int id);
}

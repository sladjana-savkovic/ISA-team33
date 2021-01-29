package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;

public interface DrugOfferRepository extends JpaRepository<DrugOffer, Integer> {

	@Query("select o from DrugOffer o where o.pharmacyOrder.id = ?1")
	Collection<DrugOffer> findByPharmacyOrderId(int id);
}

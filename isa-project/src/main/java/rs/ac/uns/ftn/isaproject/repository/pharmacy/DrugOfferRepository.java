package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;

public interface DrugOfferRepository extends JpaRepository<DrugOffer, Integer> {

	@Query("select o from DrugOffer o where o.pharmacyOrder.id = ?1")
	Collection<DrugOffer> findByPharmacyOrderId(int id);
	
	@Query("select o from DrugOffer o where o.pharmacyOrder.pharmacyAdministrator.pharmacy.id = ?1")
	Collection<DrugOffer> findByPharmacyId(int id);
	
	@Query("select o.supplier from DrugOffer o where o.id = ?1")
	Supplier findSupplierById(int id);
}

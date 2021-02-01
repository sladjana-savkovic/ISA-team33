package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;

public interface PharmacyOrderRepository extends JpaRepository<PharmacyOrder, Integer> {

	@Query("select max(id) from PharmacyOrder")
	int findByMaxId();
	
	@Query("select o from PharmacyOrder o where o.pharmacyAdministrator.pharmacy.id = ?1")
	Collection<PharmacyOrder> findByPharmacyId(int id);
}

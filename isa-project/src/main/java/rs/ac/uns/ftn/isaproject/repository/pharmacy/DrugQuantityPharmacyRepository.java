package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;

public interface DrugQuantityPharmacyRepository extends JpaRepository<DrugQuantityPharmacy, Integer> {

	@Query("select d from DrugQuantityPharmacy d where d.pharmacy.id = ?1 and is_deleted = false")
	Collection<DrugQuantityPharmacy> findByPharmacyId(int id);
	
	@Query(value = "select * from drug_quantity_pharmacy d where d.pharmacy_id = ?1 and d.quantity > 0 and d.is_deleted = false", nativeQuery = true)
	Collection<DrugQuantityPharmacy> findAvailableDrugsByPharmacyId(int pharmacyId);
}

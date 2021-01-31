package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;

public interface DrugQuantityPharmacyRepository extends JpaRepository<DrugQuantityPharmacy, Integer> {

	@Query("select d from DrugQuantityPharmacy d where d.pharmacy.id = ?1")
	Collection<DrugQuantityPharmacy> findByPharmacyId(int id);
}

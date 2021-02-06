package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Pricelist;

public interface PricelistRepository extends JpaRepository<Pricelist, Integer>{
	
	@Query(value = "select * from Pricelist pr, Drug_Quantity_Pharmacy dqp "
			+ " where pr.drug_id=dqp.drug_id and dqp.quantity > 0 and pr.drug_id=?1 and pr.pharmacy_id=dqp.pharmacy_id", nativeQuery = true)	
	Collection<Pricelist> getByDrugId(int drugId);		

	@Query("select p from Pricelist p where p.pharmacy.id = ?1")
	Collection<Pricelist> findByPharmacyId(int id);
	
	@Query("select p from Pricelist p where p.drug.id = ?1")
	Collection<Pricelist> findByDrugId(int id);

}

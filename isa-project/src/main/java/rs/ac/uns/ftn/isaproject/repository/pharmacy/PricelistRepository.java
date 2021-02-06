package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Pricelist;

public interface PricelistRepository extends JpaRepository<Pricelist, Integer>{

	@Query("select p from Pricelist p where p.pharmacy.id = ?1")
	Collection<Pricelist> findByPharmacyId(int id);
	
	@Query("select p from Pricelist p where p.drug.id = ?1")
	Collection<Pricelist> findByDrugId(int id);
}

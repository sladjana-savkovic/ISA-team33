package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;


public interface DrugRepository extends JpaRepository<Drug, Integer> {

	@Query("select o from Drug o where o.id in :ids")
	Collection<Drug> findByIds(Collection<Integer> ids);	
	
}

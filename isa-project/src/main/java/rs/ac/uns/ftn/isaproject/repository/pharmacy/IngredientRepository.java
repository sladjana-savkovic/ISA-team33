package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{
	
	@Query("select o from Ingredient o where o.id in :ids")
	Collection<Ingredient> findByIds(Collection<Integer> ids);	

}

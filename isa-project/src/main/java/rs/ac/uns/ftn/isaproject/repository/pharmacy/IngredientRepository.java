package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{

}

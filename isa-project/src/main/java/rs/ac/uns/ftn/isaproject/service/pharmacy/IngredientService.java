package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Ingredient;

public interface IngredientService {

	//void add(DrugDTO drugDTO);
	Collection<Ingredient> getAllIngredients();
}

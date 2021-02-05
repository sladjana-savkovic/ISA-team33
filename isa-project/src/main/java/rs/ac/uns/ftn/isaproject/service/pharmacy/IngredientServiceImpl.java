package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Ingredient;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.IngredientRepository;

@Service
public class IngredientServiceImpl implements IngredientService {
	
	private IngredientRepository ingredientRepository;
	
	@Autowired
	public IngredientServiceImpl(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public Collection<Ingredient> getAllIngredients() {
		return ingredientRepository.findAll();
	}
	
}

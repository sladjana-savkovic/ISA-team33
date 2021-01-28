package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.IngredientDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Ingredient;

public class IngredientMapper {

	public static Collection<IngredientDTO> toIngredientDTOs(Collection<Ingredient> ingredients){
		
		Collection<IngredientDTO> ingredientDTOs = new ArrayList<>();
		
		for(Ingredient i:ingredients) {
			ingredientDTOs.add(new IngredientDTO(i.getId(), i.getName()));
		}
		
		return ingredientDTOs;
	}
}

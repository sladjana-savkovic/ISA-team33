package rs.ac.uns.ftn.isaproject.mapper;

import java.util.HashSet;
import java.util.Set;

import rs.ac.uns.ftn.isaproject.dto.IngredientDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Ingredient;

public class IngredientMapper {

	public static Set<IngredientDTO> toIngredientDTOs(Set<Ingredient> ingredients){
		
		Set<IngredientDTO> ingredientDTOs = new HashSet<IngredientDTO>();
		
		for(Ingredient i:ingredients) {
			ingredientDTOs.add(new IngredientDTO(i.getId(), i.getName()));
		}
		
		return ingredientDTOs;
	}
}

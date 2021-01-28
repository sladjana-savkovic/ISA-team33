package rs.ac.uns.ftn.isaproject.mapper;

import java.util.HashSet;
import java.util.Set;

import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.IngredientDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

public class DrugMapper {

	public static Set<DrugDTO> toDrugDTOs(Set<Drug> drugs){
		
		Set<DrugDTO> drugDTOs = new HashSet<DrugDTO>();
		
		for(Drug d:drugs) {
			Set<IngredientDTO> ingredients = IngredientMapper.toIngredientDTOs(d.getIngredients());
			drugDTOs.add(new DrugDTO(d.getId(), d.getName(), d.getTypeOfDrug().toString(), d.getTypeOfDrugsForm().toString(), d.getProducer(), ingredients));
		}
		
		return drugDTOs;
	}
}

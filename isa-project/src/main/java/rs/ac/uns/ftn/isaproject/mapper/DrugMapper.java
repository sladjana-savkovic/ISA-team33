package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.IngredientDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

public class DrugMapper {

	public static Collection<DrugDTO> toDrugDTOs(Collection<Drug> drugs){
		
		Collection<DrugDTO> drugDTOs = new ArrayList<>();
		
		for(Drug d:drugs) {
			Collection<IngredientDTO> ingredients = IngredientMapper.toIngredientDTOs(d.getIngredients());
			drugDTOs.add(new DrugDTO(d.getId(), d.getName(), d.getTypeOfDrug().name(), d.getTypeOfDrugsForm().name(), d.getProducer(), ingredients,
									d.getContraindication(),d.getDailyDose()));
		}
		
		return drugDTOs;
	}
}

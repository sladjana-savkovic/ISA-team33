package rs.ac.uns.ftn.isaproject.dto;

import java.util.Set;

public class DrugDTO {

	public int id;
	public String name;
	public String typeOfDrug;
	public String typeOfDrugsForm;
	public String producer;
	public Set<IngredientDTO> ingredients;
	
	public DrugDTO() {}
	
	public DrugDTO(int id, String name, String typeOfDrug, String typeOfDrugsForm, String producer, Set<IngredientDTO> ingredientDTOs) {
		super();
		this.id = id;
		this.name = name;
		this.typeOfDrug = typeOfDrug;
		this.typeOfDrugsForm = typeOfDrugsForm;
		this.producer = producer;
		this.ingredients = ingredientDTOs;
	}
	
}

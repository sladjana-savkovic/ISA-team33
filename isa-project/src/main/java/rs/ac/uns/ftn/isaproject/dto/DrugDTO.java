package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;

public class DrugDTO {

	public int id;
	public String name;
	public String typeOfDrug;
	public String typeOfDrugsForm;
	public String producer;
	public Collection<IngredientDTO> ingredients;
	public String contraindication;
	public int dailyDose;
	
	public DrugDTO() {}
	
	public DrugDTO(int id, String name, String typeOfDrug, String typeOfDrugsForm, String producer, Collection<IngredientDTO> ingredientDTOs,
					String contraindication, int dailyDose) {
		super();
		this.id = id;
		this.name = name;
		this.typeOfDrug = typeOfDrug;
		this.typeOfDrugsForm = typeOfDrugsForm;
		this.producer = producer;
		this.ingredients = ingredientDTOs;
		this.contraindication = contraindication;
		this.dailyDose = dailyDose;
	}
	
}

package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;

public class AddDrugDTO {

	public int id;
	public String name;
	public String code;
	public String contraindication;
	public String notes;
	public String producer;
	public String typeOfDrug;
	public String drugForm;
	public boolean prescription;	
	public int dailyDose;
	public Collection<Integer> substituteDrugList;
	public Collection<Integer> drugIngredientsList;
	
	public AddDrugDTO() {}
			
}

package rs.ac.uns.ftn.isaproject.model.pharmacy;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrug;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrugsForm;

@Entity
public class Drug {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = false, nullable = false)
	private String name;
	
	@Column(unique = false, nullable = false)
	private TypeOfDrug typeOfDrug;
	
	@Column(unique = false, nullable = false)
	private TypeOfDrugsForm typeOfDrugsForm;
	
	@Column(unique = false, nullable = false)
	private String producer;

	@ManyToMany
	@JoinTable(name = "drug_pharmacy", joinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
	private Set<Pharmacy> pharmacies = new HashSet<Pharmacy>();
	
	@ManyToMany
	@JoinTable(name = "drug_ingredient", joinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();

	public Drug() {}
	
	public Drug(int id, String name, TypeOfDrug typeOfDrug, TypeOfDrugsForm typeOfDrugsForm, String producer) {
		super();
		this.id = id;
		this.name = name;
		this.typeOfDrug = typeOfDrug;
		this.typeOfDrugsForm = typeOfDrugsForm;
		this.producer = producer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeOfDrug getTypeOfDrug() {
		return typeOfDrug;
	}

	public void setTypeOfDrug(TypeOfDrug typeOfDrug) {
		this.typeOfDrug = typeOfDrug;
	}

	public TypeOfDrugsForm getTypeOfDrugsForm() {
		return typeOfDrugsForm;
	}

	public void setTypeOfDrugsForm(TypeOfDrugsForm typeOfDrugsForm) {
		this.typeOfDrugsForm = typeOfDrugsForm;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public Set<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(Set<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	
}

package rs.ac.uns.ftn.isaproject.model.pharmacy;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrug;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrugsForm;

@Entity
public class Drug {

	@Id
	@SequenceGenerator(name = "drugsSeqGen", sequenceName = "drugsSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drugsSeqGen")
	private int id;
	
	@Column(unique = false, nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String code;
	
	@Column(unique = false, nullable = true)
	private String notes;	
	
	@Column(unique = false, nullable = false)
	private TypeOfDrug typeOfDrug;
	
	@Column(unique = false, nullable = false)
	private TypeOfDrugsForm typeOfDrugsForm;
	
	@Column(unique = false, nullable = false)
	private String producer;
	
	@Column(unique = false, nullable = false)
	private String contraindication;
	
	@Column(unique = false, nullable = false)
	private int dailyDose;
		
	@Column(unique = false, nullable = false)
	private boolean isAllowedOnPrescription;

	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Drug> substituteDrugs = new HashSet<Drug>();
	

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

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public Set<Drug> getSubstituteDrugs() {
		return substituteDrugs;
	}

	public void setSubstituteDrugs(Set<Drug> substituteDrugs) {
		this.substituteDrugs = substituteDrugs;
	}

	public String getContraindication() {
		return contraindication;
	}

	public void setContraindication(String contraindication) {
		this.contraindication = contraindication;
	}

	public int getDailyDose() {
		return dailyDose;
	}

	public void setDailyDose(int dailyDose) {
		this.dailyDose = dailyDose;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isAllowedOnPrescription() {
		return isAllowedOnPrescription;
	}

	public void setAllowedOnPrescription(boolean isAllowedOnPrescription) {
		this.isAllowedOnPrescription = isAllowedOnPrescription;
	}	
	
}

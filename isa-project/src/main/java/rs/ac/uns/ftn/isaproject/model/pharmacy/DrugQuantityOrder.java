package rs.ac.uns.ftn.isaproject.model.pharmacy;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import rs.ac.uns.ftn.isaproject.model.enums.PurposeOfDrugQuantity;

@Entity
public class DrugQuantityOrder {

	@Id
	@SequenceGenerator(name = "drugquantitiesSeqGen", sequenceName = "drugquantitiesSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drugquantitiesSeqGen")
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Drug drug;
	
	@Column(unique=false, nullable=false)
	private int quantity;
	
	@Column(unique=false, nullable=false)
	private PurposeOfDrugQuantity purpose;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private PharmacyOrder pharmacyOrder;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public PurposeOfDrugQuantity getPurpose() {
		return purpose;
	}

	public void setPurpose(PurposeOfDrugQuantity purpose) {
		this.purpose = purpose;
	}

	public PharmacyOrder getPharmacyOrder() {
		return pharmacyOrder;
	}

	public void setPharmacyOrder(PharmacyOrder pharmacyOrder) {
		this.pharmacyOrder = pharmacyOrder;
	}
	
}

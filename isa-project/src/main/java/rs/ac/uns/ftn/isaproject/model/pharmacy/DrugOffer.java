package rs.ac.uns.ftn.isaproject.model.pharmacy;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class DrugOffer {

	@Id
	@SequenceGenerator(name = "offersSeqGen", sequenceName = "offersSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offersSeqGen")
	private int id;
	
	@Column(unique = false, nullable = false)
	private double totalPrice;
	
	@Column(unique = false, nullable = false)
	private boolean isAccepted;
	
	@Column(unique=false, nullable=false)
	private LocalDate limitDate;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private PharmacyOrder pharmacyOrder;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public LocalDate getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(LocalDate limitDate) {
		this.limitDate = limitDate;
	}

	public PharmacyOrder getPharmacyOrder() {
		return pharmacyOrder;
	}

	public void setPharmacyOrder(PharmacyOrder pharmacyOrder) {
		this.pharmacyOrder = pharmacyOrder;
	}
	
}
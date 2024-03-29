package rs.ac.uns.ftn.isaproject.model.pharmacy;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import rs.ac.uns.ftn.isaproject.model.users.PharmacyAdministrator;

@Entity
public class PharmacyOrder {

	@Id
	@SequenceGenerator(name = "ordersSeqGen", sequenceName = "ordersSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordersSeqGen")
	private int id;
	
	@Column(unique=false, nullable=false)
	private LocalDate limitDate;
	
	@Column(unique=false, nullable=false)
	private boolean isFinished;
	
	@Column(unique=false, nullable=false)
	private boolean isDeleted;
	
	@OneToMany(mappedBy = "pharmacyOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DrugQuantityOrder> drugQuantityOrders = new HashSet<DrugQuantityOrder>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private PharmacyAdministrator pharmacyAdministrator;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(LocalDate limitDate) {
		this.limitDate = limitDate;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public PharmacyAdministrator getPharmacyAdministrator() {
		return pharmacyAdministrator;
	}

	public void setPharmacyAdministrator(PharmacyAdministrator pharmacyAdministrator) {
		this.pharmacyAdministrator = pharmacyAdministrator;
	}

	public Set<DrugQuantityOrder> getDrugQuantityOrders() {
		return drugQuantityOrders;
	}

	public void setDrugQuantityOrders(Set<DrugQuantityOrder> drugQuantityOrders) {
		this.drugQuantityOrders = drugQuantityOrders;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}

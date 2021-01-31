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

import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.users.Patient;

@Entity
public class PharmacyAction {

	@Id
	@SequenceGenerator(name = "actionsSeqGen", sequenceName = "actionsSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actionsSeqGen")
	private int id;
	
	@Column(unique = false, nullable = false)
	private String name;
	
	@Column(unique = false, nullable = false)
	private String description;
	
	@Column(unique=false, nullable=false)
	private LocalDate startDate;
	
	@Column(unique=false, nullable=false)
	private LocalDate endDate;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Pharmacy pharmacy;
	
	@OneToMany(mappedBy = "pharmacyAction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Subscription> subscriptions = new HashSet<Subscription>();


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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	public PharmacyAction() {}
	
}

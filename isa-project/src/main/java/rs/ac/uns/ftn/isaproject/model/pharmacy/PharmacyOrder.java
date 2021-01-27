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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

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
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OrderedDrug> orderedDrugs = new HashSet<OrderedDrug>();

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

	public Set<OrderedDrug> getOrderedDrugs() {
		return orderedDrugs;
	}

	public void setOrderedDrugs(Set<OrderedDrug> orderedDrugs) {
		this.orderedDrugs = orderedDrugs;
	}
	
}

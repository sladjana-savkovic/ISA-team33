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

import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.enums.OfferStatus;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;

@Entity
public class Offer {

	@Id
	@SequenceGenerator(name = "offersSeqGen", sequenceName = "offersSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offersSeqGen")
	private int id;
	
	@Column(unique=false, nullable=false)
	private OfferStatus status;
	
	@Column(unique=false, nullable=false)
	private LocalDate deliveryTime;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Supplier supplier;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}
	
}

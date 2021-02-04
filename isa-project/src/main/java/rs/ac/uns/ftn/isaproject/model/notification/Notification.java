package rs.ac.uns.ftn.isaproject.model.notification;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;

@Entity
public class Notification {
	
	@Id
	@SequenceGenerator(name = "notificationSeqGen", sequenceName = "notificationSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationSeqGen")
	private int id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Drug drug;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Pharmacy pharmacy;

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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
}
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

import rs.ac.uns.ftn.isaproject.model.users.Patient;


@Entity
public class Subscription {
	
	@Id
	@SequenceGenerator(name = "subscriptionsSeqGen", sequenceName = "subscriptionsSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscriptionsSeqGen")
	private int id;

	@Column(unique = false, nullable = false)
	private boolean isCanceled;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private PharmacyAction pharmacyAction;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Patient patient;
}

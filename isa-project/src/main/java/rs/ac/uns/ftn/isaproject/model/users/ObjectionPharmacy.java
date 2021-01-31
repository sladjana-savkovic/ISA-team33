package rs.ac.uns.ftn.isaproject.model.users;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;


@Entity
public class ObjectionPharmacy {
	
	@Id
	@SequenceGenerator(name = "objectionPharmacySeqGen", sequenceName = "objectionPharmacySeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "objectionPharmacySeqGen")
	private int id;

	@Column(unique=false, nullable=false)
	private String text;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Patient patient;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Pharmacy pharmacy;
	
}

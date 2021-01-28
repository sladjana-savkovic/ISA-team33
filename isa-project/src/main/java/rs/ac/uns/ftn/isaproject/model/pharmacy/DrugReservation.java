package rs.ac.uns.ftn.isaproject.model.pharmacy;

import java.time.LocalDateTime;
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
public class DrugReservation {

	@Id
	@SequenceGenerator(name = "reservationSeqGen", sequenceName = "reservationSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservationSeqGen")
	private int id;
	
	@Column(unique=false, nullable=false)
	private LocalDateTime dateLimit;
	
	@Column(unique=false, nullable=false)
	private boolean isDone;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Patient patient;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private Drug drug;
}

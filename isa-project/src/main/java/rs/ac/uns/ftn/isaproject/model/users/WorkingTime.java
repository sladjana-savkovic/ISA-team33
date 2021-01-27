package rs.ac.uns.ftn.isaproject.model.users;

import java.time.LocalTime;
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
public class WorkingTime {

	@Id
	@SequenceGenerator(name = "workTimeSeqGen", sequenceName = "workTimeSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workTimeSeqGen")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Doctor doctor;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Pharmacy pharmacy;
	
	@Column(unique=false, nullable=false)
	private LocalTime startTime;
	
	@Column(unique=false, nullable=false)
	private LocalTime endTime;
}

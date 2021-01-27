package rs.ac.uns.ftn.isaproject.model.examinations;

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
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfAppointment;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.model.users.Patient;

@Entity
public class Appointment {

	@Id
	@SequenceGenerator(name = "appointmentsSeqGen", sequenceName = "appointmentsSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointmentsSeqGen")
	private int id;

	@Column(unique=false, nullable=false)
	private TypeOfAppointment typeOfAppointment;
	
	@Column(unique=false, nullable=false)
	private LocalDateTime startTime;
	
	@Column(unique=false, nullable=false)
	private LocalDateTime endTime;
	
	@Column(unique=false, nullable=false)
	private double price;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Doctor doctor;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Pharmacy pharmacy;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Patient patient;
}

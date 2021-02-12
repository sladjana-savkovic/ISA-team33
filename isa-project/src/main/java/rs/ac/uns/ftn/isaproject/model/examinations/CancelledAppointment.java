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
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.model.users.Patient;

@Entity
public class CancelledAppointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public CancelledAppointment() {}
	
	@Column(unique=false, nullable=false)
	private LocalDateTime startTime;
	
	public CancelledAppointment(LocalDateTime startTime, LocalDateTime endTime, Doctor doctor, Pharmacy pharmacy,
			Patient patient) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.doctor = doctor;
		this.pharmacy = pharmacy;
		this.patient = patient;
	}

	@Column(unique=false, nullable=false)
	private LocalDateTime endTime;
	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Doctor doctor;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Pharmacy pharmacy;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}


	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
}

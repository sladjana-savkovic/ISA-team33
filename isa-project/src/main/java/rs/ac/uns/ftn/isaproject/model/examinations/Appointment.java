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

import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
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
	private AppointmentStatus status;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TypeOfAppointment getTypeOfAppointment() {
		return typeOfAppointment;
	}

	public void setTypeOfAppointment(TypeOfAppointment typeOfAppointment) {
		this.typeOfAppointment = typeOfAppointment;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}
	
}

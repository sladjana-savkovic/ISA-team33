package rs.ac.uns.ftn.isaproject.model.users;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;

@Entity
public class Doctor extends User{

	@Column(unique=false, nullable=false)
	private TypeOfDoctor typeOfDoctor;
	
	@Column(unique=false, nullable=true)
	private double averageGrade;
	
	@Column(unique=false, nullable=false)
	private boolean isDeleted;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "doctor_pharmacies", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "pharmacies_id", referencedColumnName = "id"))
	private Set<Pharmacy> pharmacies = new HashSet<Pharmacy>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<WorkingTime> workingTimes = new HashSet<WorkingTime>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<VacationRequest> approvedVacations = new HashSet<VacationRequest>();
	

	public TypeOfDoctor getTypeOfDoctor() {
		return typeOfDoctor;
	}

	public void setTypeOfDoctor(TypeOfDoctor typeOfDoctor) {
		this.typeOfDoctor = typeOfDoctor;
	}

	public double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(double averageGrade) {
		this.averageGrade = averageGrade;
	}

	public Set<WorkingTime> getWorkingTimes() {
		return workingTimes;
	}

	public void setWorkingTimes(Set<WorkingTime> workingTimes) {
		this.workingTimes = workingTimes;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Set<VacationRequest> getApprovedVacations() {
		return approvedVacations;
	}

	public void setApprovedVacations(Set<VacationRequest> approvedVacations) {
		this.approvedVacations = approvedVacations;
	}

	public Set<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(Set<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}

	public boolean isIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean idDeleted) {
		this.isDeleted = idDeleted;
	}
	
}

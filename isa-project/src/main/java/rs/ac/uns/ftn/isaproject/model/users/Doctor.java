package rs.ac.uns.ftn.isaproject.model.users;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

@Entity
public class Doctor extends User{

	@Column(unique=false, nullable=false)
	private TypeOfDoctor typeOfDoctor;
	
	@Column(unique=false, nullable=true)
	private double averageGrade;
	
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
	
}

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
	private double averagePrice;
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<WorkingTime> workingTimes = new HashSet<WorkingTime>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<VacationRequest> approvedVacations = new HashSet<VacationRequest>();
}

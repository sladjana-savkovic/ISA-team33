package rs.ac.uns.ftn.isaproject.model.examinations;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ExaminationReport{
	
	@Id
	@SequenceGenerator(name = "examinationsSeqGen", sequenceName = "examinationsSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examinationsSeqGen")
	private int id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Appointment appointment;
	
	@Column(unique=false, nullable=false)
	private String diagnosis;
	
	@OneToMany(mappedBy = "examination", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Therapy> therapies = new HashSet<Therapy>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Set<Therapy> getTherapies() {
		return therapies;
	}

	public void setTherapies(Set<Therapy> therapies) {
		this.therapies = therapies;
	}
	
}

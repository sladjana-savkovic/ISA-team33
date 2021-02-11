package rs.ac.uns.ftn.isaproject.model.complaint;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

@Entity
public class ComplaintDoctor {

	@Id
	@SequenceGenerator(name = "complaintSeqGen", sequenceName = "complaintSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complaintSeqGen")
    int id;
	
    @Column(name="content", unique=false, nullable=false)
    String content;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserAccount patient;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserAccount doctor;
	
	@Column(name="answered", unique=false, nullable=false)
	private boolean answered;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public UserAccount getPatient() {
		return patient;
	}

	public void setPatient(UserAccount patient) {
		this.patient = patient;
	}

	public UserAccount getDoctor() {
		return doctor;
	}

	public void setDoctor(UserAccount doctor) {
		this.doctor = doctor;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}
	
}

package rs.ac.uns.ftn.isaproject.model.examinations;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

@Entity
public class Therapy {

	@Id
	@SequenceGenerator(name = "therapiesSeqGen", sequenceName = "therapiesSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "therapiesSeqGen")
	private int id;
	
	@Column(unique=false, nullable=false)
	private int duration;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Drug drug;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private ExaminationReport examination;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Drug getDrug() {
		return drug;
	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public ExaminationReport getExamination() {
		return examination;
	}

	public void setExamination(ExaminationReport examination) {
		this.examination = examination;
	}
	
	
}

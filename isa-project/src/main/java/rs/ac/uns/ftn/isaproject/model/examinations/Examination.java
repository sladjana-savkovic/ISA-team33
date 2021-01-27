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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfExamination;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

@Entity
public class Examination{
	
	@Id
	@SequenceGenerator(name = "examinationsSeqGen", sequenceName = "examinationsSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examinationsSeqGen")
	private int id;
	
	@Column(unique=false, nullable=false)
	private LocalDateTime dateTime;
	
	@Column(unique=false, nullable=false)
	private String diagnosis;
	
	@Column(unique=false, nullable=false)
	private TypeOfExamination typeOfExamination;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Doctor doctor;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Pharmacy pharmacy;
	
}

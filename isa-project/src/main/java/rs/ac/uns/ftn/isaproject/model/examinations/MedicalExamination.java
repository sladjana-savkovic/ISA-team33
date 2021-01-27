package rs.ac.uns.ftn.isaproject.model.examinations;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.SequenceGenerator;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class MedicalExamination {

	@Id
	@SequenceGenerator(name = "examinationsSeqGen", sequenceName = "examinationsSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "examinationsSeqGen")
	private int id;
	
	@Column(unique=false, nullable=false)
	private LocalDateTime dateTime;
	
	@Column(unique=false, nullable=false)
	private String diagnosis;
}

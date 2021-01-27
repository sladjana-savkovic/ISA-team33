package rs.ac.uns.ftn.isaproject.model.users;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import rs.ac.uns.ftn.isaproject.model.enums.VacationRequestStatus;

@Entity
public class VacationRequest {

	@Id
	@SequenceGenerator(name = "vacationSeqGen", sequenceName = "vacationSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacationSeqGen")
	private int id;
	
	@Column(unique=false, nullable=false)
	private LocalDate startDate;
	
	@Column(unique=false, nullable=false)
	private LocalDate endDate;
	
	@Column(unique=false, nullable=false)
	private VacationRequestStatus status;
	
	@Column(unique=false, nullable=true)
	private String reasonForRejection;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	private Doctor doctor;
}

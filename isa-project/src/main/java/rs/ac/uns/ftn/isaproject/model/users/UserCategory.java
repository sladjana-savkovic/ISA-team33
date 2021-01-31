package rs.ac.uns.ftn.isaproject.model.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class UserCategory {
	
	@Id
	@SequenceGenerator(name = "categorySeqGen", sequenceName = "categorySeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySeqGen")
	private int id;

	@Column(unique=false, nullable=false)
	private String name;
	
	@Column(unique=false, nullable=false)
	private double discount;
	
	@Column(unique=false, nullable=false)
	private int upperLimit;
	
	@Column(unique=false, nullable=false)
	private int lowerLimit;
	
}

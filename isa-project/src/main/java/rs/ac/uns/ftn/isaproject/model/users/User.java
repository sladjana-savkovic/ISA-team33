package rs.ac.uns.ftn.isaproject.model.users;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import rs.ac.uns.ftn.isaproject.model.geographical.City;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class User {

	@Id
	@SequenceGenerator(name = "usersSeqGen", sequenceName = "usersSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSeqGen")
	private int id;
	
	@Column(unique=false, nullable=false)
	private String name;
	
	@Column(unique=false, nullable=false)
	private String surname;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(unique=false, nullable=false)
	private String password;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private City city;
}

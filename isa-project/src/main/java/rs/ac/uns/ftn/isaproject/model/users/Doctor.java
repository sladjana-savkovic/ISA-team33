package rs.ac.uns.ftn.isaproject.model.users;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.examinations.Examination;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;

@Entity
public class Doctor extends User{

	@Column(unique=false, nullable=false)
	private TypeOfDoctor typeOfDoctor;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Pharmacy> pharmacies = new HashSet<Pharmacy>();
	
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Examination> examinations = new HashSet<Examination>();
}

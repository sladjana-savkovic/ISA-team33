package rs.ac.uns.ftn.isaproject.model.users;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

@Entity
public class Patient extends User {

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<ExaminationReport> examinationReports = new HashSet<ExaminationReport>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Drug> allergies = new HashSet<Drug>();
}

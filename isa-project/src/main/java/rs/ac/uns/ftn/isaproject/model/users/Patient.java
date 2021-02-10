package rs.ac.uns.ftn.isaproject.model.users;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Subscription;

@Entity
public class Patient extends User {
	

	@Column(unique=false, nullable=false)
	private int penalty;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Drug> allergies = new HashSet<Drug>();
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Subscription> subscriptions = new HashSet<Subscription>();
		
	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public Set<Drug> getAllergies() {
		return allergies;
	}

	public void setAllergies(Set<Drug> allergies) {
		this.allergies = allergies;
	}

	
}

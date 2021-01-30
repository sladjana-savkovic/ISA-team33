package rs.ac.uns.ftn.isaproject.model.users;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Offer;

@Entity
public class Supplier extends User{

	@OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Offer> offers = new HashSet<Offer>();

}

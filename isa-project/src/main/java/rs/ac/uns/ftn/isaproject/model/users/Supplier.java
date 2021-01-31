package rs.ac.uns.ftn.isaproject.model.users;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;


@Entity
public class Supplier extends User{

	@OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DrugOffer> drugOffers = new HashSet<DrugOffer>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DrugQuantityOrder> drugsInStore = new HashSet<DrugQuantityOrder>();
}

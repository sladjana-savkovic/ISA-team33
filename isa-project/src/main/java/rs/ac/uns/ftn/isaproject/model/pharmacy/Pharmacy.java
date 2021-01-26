package rs.ac.uns.ftn.isaproject.model.pharmacy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import rs.ac.uns.ftn.isaproject.model.geographical.City;

@Entity
public class Pharmacy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = false, nullable = false)
	private String name;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private City city;
	
	public Pharmacy() {}

	public Pharmacy(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}

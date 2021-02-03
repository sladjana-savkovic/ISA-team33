package rs.ac.uns.ftn.isaproject.model.users;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.time.LocalDate;
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
	
	@Column(unique=false, nullable=false)
	private LocalDate dateOfBirth;
	
	@Column(unique=true, nullable=true)
	private String email;
	
	@Column(unique=false, nullable=true)
	private String password;
	
	@Column(unique=false, nullable=false)
	private String address;
	
	@Column(unique=false, nullable=false)
	private String telephone;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private City city;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}

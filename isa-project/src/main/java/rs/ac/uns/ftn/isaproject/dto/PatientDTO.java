package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

public class PatientDTO {

	public int id;
	public String name;
	public String surname;
	public LocalDate dateOfBirth;
	public String email;
	public String password;
	public int countryId;
	public String countryName;
	public int cityId;
	public String cityName;
	public String address;
	public String telephone;
	public Set<Drug> allergies;
	public List<Integer> allergyIds;
	
	public PatientDTO() {}

	public PatientDTO(int id, String name, String surname, LocalDate dateOfBirth, String email, String password,
			int countryId, String countryName, int cityId, String cityName, String address, String telephone, Set<Drug> allergies) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = password;
		this.countryId = countryId;
		this.countryName = countryName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.address = address;
		this.telephone = telephone;
		this.allergies = allergies;
	}	
	
	
}

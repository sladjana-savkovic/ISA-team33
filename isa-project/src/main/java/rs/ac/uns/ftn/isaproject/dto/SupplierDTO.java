package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class SupplierDTO {

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
	
	public SupplierDTO() {}

	public SupplierDTO(int id, String name, String surname, LocalDate dateOfBirth, String email, String password,
			int countryId, String countryName, int cityId, String cityName, String address, String telephone) {
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
	}	
	
	
}

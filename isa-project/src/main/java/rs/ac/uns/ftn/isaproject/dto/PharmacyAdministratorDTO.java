package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class PharmacyAdministratorDTO {

	public int id;
	public String name;
	public String surname;
	public LocalDate dateOfBirth;
	public String email;
	public int countryId;
	public String countryName;
	public int cityId;
	public String cityName;
	public String address;
	public int pharmacyId;
	public String telephone;
	
	public PharmacyAdministratorDTO() {}

	public PharmacyAdministratorDTO(int id, String name, String surname, LocalDate dateOfBirth,
			String address, int cityId, String cityName, String countryName, int pharmacyId, int countryId, String telephone) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.cityId = cityId;
		this.cityName = cityName;
		this.countryName = countryName;
		this.pharmacyId = pharmacyId;
		this.countryId = countryId;
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}

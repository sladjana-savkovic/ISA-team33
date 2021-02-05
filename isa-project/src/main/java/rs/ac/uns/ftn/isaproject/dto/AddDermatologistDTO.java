package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class AddDermatologistDTO {

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
	public String phoneNumber;
	
	public AddDermatologistDTO() {}

	public AddDermatologistDTO(int id, String name, String surname, LocalDate dateOfBirth, String email, String password,
			String address, int cityId, String cityName,int countryId, String countryName, String phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.password = password;
		this.address = address;
		this.cityId = cityId;
		this.cityName = cityName;
		this.countryId = countryId;
		this.countryName = countryName;
		this.phoneNumber = phoneNumber;
	}	
}

package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class AddSystemAdministratorDTO {
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
	
	public AddSystemAdministratorDTO() {}

}

package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class DoctorDTO {

	public int id;
	public String name;
	public String surname;
	public LocalDate dateOfBirth;
	public String email;
	public String password;
	public String countryName;
	public int cityId;
	public String cityName;
	public String address;
	public double averageGrade;
	public String typeOfDoctor;
	
	public DoctorDTO() {}

	public DoctorDTO(int id, String name, String surname, LocalDate dateOfBirth, String email, String password,
			String address, int cityId, String cityName, String countryName, double averageGrade, String typeOfDoctor) {
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
		this.countryName = countryName;
		this.averageGrade = averageGrade;
		this.typeOfDoctor = typeOfDoctor;
	}	
	
}

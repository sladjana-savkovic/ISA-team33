package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;
import java.util.Collection;

public class AddDoctorDTO {

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
	public double averageGrade;
	public String typeOfDoctor;
	public String phoneNumber;
	public int pharmacyId;
	
	public AddDoctorDTO() {}

	public AddDoctorDTO(int id, String name, String surname, LocalDate dateOfBirth, String email, String password,
			String address, int cityId, String cityName,int countryId, String countryName, double averageGrade, String typeOfDoctor,String phoneNumber, int pharmacyId) {
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
		this.averageGrade = averageGrade;
		this.typeOfDoctor = typeOfDoctor;
		this.phoneNumber = phoneNumber;
		this.pharmacyId = pharmacyId;
	}	
}

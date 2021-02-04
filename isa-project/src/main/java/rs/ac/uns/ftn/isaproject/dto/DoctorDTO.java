package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class DoctorDTO {

	public int id;
	public String name;
	public String surname;
	public LocalDate dateOfBirth;
	public int countryId;
	public String countryName;
	public int cityId;
	public String cityName;
	public String address;
	public double averageGrade;
	public String typeOfDoctor;
	public String phoneNumber;
	
	public DoctorDTO() {}

	public DoctorDTO(int id, String name, String surname, LocalDate dateOfBirth,
			String address, int cityId, String cityName,int countryId, String countryName, double averageGrade, String typeOfDoctor,String phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.cityId = cityId;
		this.cityName = cityName;
		this.countryId = countryId;
		this.countryName = countryName;
		this.averageGrade = averageGrade;
		this.typeOfDoctor = typeOfDoctor;
		this.phoneNumber = phoneNumber;
	}	
	
}

package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;


public class PharmacyDTO {

	public int id;
	public String name;
	public double averageGrade;
	public String address;
	public int cityId;
	public String cityName;
	public String countryName;
	public double latitude;
	public double longitude;
	public Collection<DoctorDTO> doctors;
	public Collection<AppointmentDTO> appointments;
	public double pharmacistPrice;
	
	public PharmacyDTO() {}

	public PharmacyDTO(int id, String name, double averagePrice, String address, int cityId, String cityName, String countryName, Collection<AppointmentDTO> appointments, Collection<DoctorDTO> doctors, double latitude, double longitude, double pharmacistPrice) {
		super();
		this.id = id;
		this.name = name;
		this.averageGrade = averagePrice;
		this.address = address;
		this.cityId = cityId;
		this.cityName = cityName;
		this.countryName = countryName;
		this.doctors = doctors;
		this.appointments = appointments;
		this.latitude = latitude;
		this.longitude = longitude;
		this.pharmacistPrice = pharmacistPrice;
	}
	
}

package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;


public class PharmacyDTO {

	public int id;
	public String name;
	public double averagePrice;
	public String address;
	public int cityId;
	public String cityName;
	public String countryName;
	public Collection<DoctorDTO> doctors;
	public Collection<DrugDTO> drugs;
	public Collection<AppointmentDTO> appointments;
	
	public PharmacyDTO() {}

	public PharmacyDTO(int id, String name, double averagePrice, String address, int cityId, String cityName, String countryName, Collection<DrugDTO> drugs, Collection<AppointmentDTO> appointments, Collection<DoctorDTO> doctors) {
		super();
		this.id = id;
		this.name = name;
		this.averagePrice = averagePrice;
		this.address = address;
		this.cityId = cityId;
		this.cityName = cityName;
		this.countryName = countryName;
		this.doctors = doctors;
		this.drugs = drugs;
		this.appointments = appointments;
	}
	
}

package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public class SearchDoctorDTO {

	public int pharmacyId;
	public String name;
	public String surname;
	public Collection<Doctor> doctors;
	
	public SearchDoctorDTO() {}
	
	public SearchDoctorDTO(int pharmacyId, String name, String surname, Collection<Doctor> doctors) {
		super();
		this.pharmacyId = pharmacyId;
		this.name = name;
		this.surname = surname;
		this.doctors = doctors;
	}

}

package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;


public class SearchDoctorDTO {

	public int pharmacyId;
	public String name;
	public String surname;
	public Collection<ViewSearchedDoctorDTO> doctors;
	
	public SearchDoctorDTO() {}
	
	public SearchDoctorDTO(int pharmacyId, String name, String surname, Collection<ViewSearchedDoctorDTO> doctors) {
		super();
		this.pharmacyId = pharmacyId;
		this.name = name;
		this.surname = surname;
		this.doctors = doctors;
	}

}

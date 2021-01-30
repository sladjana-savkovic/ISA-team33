package rs.ac.uns.ftn.isaproject.dto;

public class SearchDoctorDTO {

	public int pharmacyId;
	public String name;
	public String surname;
	
	public SearchDoctorDTO() {}
	
	public SearchDoctorDTO(int pharmacyId, String name, String surname) {
		super();
		this.pharmacyId = pharmacyId;
		this.name = name;
		this.surname = surname;
	}

}

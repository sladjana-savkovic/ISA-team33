package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;


public class PharmacyActionDTO {
	
	public int id;
	public String name;
	public String description;
	public LocalDate startDate;
	public LocalDate endDate;
	public int pharmacyId;
	
	public PharmacyActionDTO() {}
	
	public PharmacyActionDTO(int id, String name, String description, LocalDate startDate, LocalDate endDate,
			int pharmacyId) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pharmacyId = pharmacyId;
	}

}

package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;

public class PharmacySearchDTO {

	public String name;
	public double gradeMin;
	public double gradeMax;
	public double priceMin;
	public double priceMax;
	public String address;
	public String cityName;
	
	public Collection<PharmacyDTO> pharmacyDTOs;
	
	public PharmacySearchDTO() { }
	
}

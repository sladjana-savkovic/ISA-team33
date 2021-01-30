package rs.ac.uns.ftn.isaproject.dto;

import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;

public class FilterDoctorDTO {

	public int pharmacyId;
	public double grade;
	public TypeOfDoctor typeOfDoctor;
	
	public FilterDoctorDTO() {}
	
	public FilterDoctorDTO(int pharmacyId, double grade, TypeOfDoctor typeOfDoctor) {
		super();
		this.pharmacyId = pharmacyId;
		this.grade = grade;
		this.typeOfDoctor = typeOfDoctor;
	}
	
}

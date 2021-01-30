package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;

public class FilterDoctorDTO {

	public int pharmacyId;
	public double grade;
	public TypeOfDoctor typeOfDoctor;
	public Collection<ViewSearchedDoctorDTO> doctors;
	
	public FilterDoctorDTO() {}
	
	public FilterDoctorDTO(int pharmacyId, double grade, TypeOfDoctor typeOfDoctor, Collection<ViewSearchedDoctorDTO> doctors) {
		super();
		this.pharmacyId = pharmacyId;
		this.grade = grade;
		this.typeOfDoctor = typeOfDoctor;
		this.doctors = doctors;
	}
	
}

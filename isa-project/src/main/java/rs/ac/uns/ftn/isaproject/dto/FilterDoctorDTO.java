package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public class FilterDoctorDTO {

	public int pharmacyId;
	public double grade;
	public TypeOfDoctor typeOfDoctor;
	public Collection<Doctor> doctors;
	
	public FilterDoctorDTO() {}
	
	public FilterDoctorDTO(int pharmacyId, double grade, TypeOfDoctor typeOfDoctor, Collection<Doctor> doctors) {
		super();
		this.pharmacyId = pharmacyId;
		this.grade = grade;
		this.typeOfDoctor = typeOfDoctor;
		this.doctors = doctors;
	}
	
}

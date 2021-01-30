package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;

public class ViewSearchedDoctorDTO {

	public int id;
	public String name;
	public String surname;
	public TypeOfDoctor typeOfDoctor;
	public double grade;
	public Collection<PharmacyDTO> pharmacies;
	
	public ViewSearchedDoctorDTO() {}
	
	public ViewSearchedDoctorDTO(int id, String name, String surname, double grade, Collection<PharmacyDTO> pharmacies, TypeOfDoctor typeOfDoctor) {
		super();
		this.id=id;
		this.name = name;
		this.surname = surname;
		this.grade = grade;
		this.pharmacies = pharmacies;
		this.typeOfDoctor = typeOfDoctor;
	}
	
}

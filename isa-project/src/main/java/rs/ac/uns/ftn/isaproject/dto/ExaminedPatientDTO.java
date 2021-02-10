package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;
import java.util.Collection;

public class ExaminedPatientDTO {

	public int id;
	public String name;
	public String surname;
	public LocalDate dateOfBirth;
	public String address;
	public Collection<String> allergies;
	public String dateOfLastExamination;
	
	public ExaminedPatientDTO() {}

	public ExaminedPatientDTO(int id, String name, String surname,LocalDate dateOfBirth, String address, Collection<String> alllergies,String dateOfLastExamination) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.allergies = alllergies;
		this.dateOfLastExamination = dateOfLastExamination;
	}
	
}

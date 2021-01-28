package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class ExaminedPatientDTO {

	public int id;
	public String name;
	public String surname;
	public LocalDate dateOfLastExamination;
	
	public ExaminedPatientDTO() {}

	public ExaminedPatientDTO(int id, String name, String surname,LocalDate dateOfLastExamination) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfLastExamination = dateOfLastExamination;
	}
	
}

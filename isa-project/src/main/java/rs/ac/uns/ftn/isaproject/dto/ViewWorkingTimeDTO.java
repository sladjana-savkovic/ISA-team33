package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalTime;

import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;

public class ViewWorkingTimeDTO {

	public int id;
	public int doctorId;
	public int pharmacyId;
	public LocalTime startTime;
	public LocalTime endTime;
	public String doctorName;
	public String doctorSurname;
	public TypeOfDoctor typeOfDoctor;
	
	public ViewWorkingTimeDTO() {}
	
	public ViewWorkingTimeDTO(int id, int doctorId, int pharmacyId, LocalTime startTime, LocalTime endTime,
			String doctorName, String doctorSurname, TypeOfDoctor typeOfDoctor) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.pharmacyId = pharmacyId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.doctorName = doctorName;
		this.doctorSurname = doctorSurname;
		this.typeOfDoctor = typeOfDoctor;
	}

}

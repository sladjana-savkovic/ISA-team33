package rs.ac.uns.ftn.isaproject.dto;

import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;

public class AppointmentDTO {
	
	public int id;
	public String startTime;
	public String endTime;
	public double price;
	public int idPharmacy;
	public AppointmentStatus status;
	public String doctorSurname;
	public TypeOfDoctor typeOfDoctor;
	
	public AppointmentDTO() {}
	
	public AppointmentDTO(int id, String startTime, String endTime, double price,
			int idPharmacy, AppointmentStatus status, String doctorSurname, TypeOfDoctor typeOfDoctor) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.idPharmacy = idPharmacy;
		this.status = status;
		this.doctorSurname = doctorSurname;
		this.typeOfDoctor = typeOfDoctor;
	}


}

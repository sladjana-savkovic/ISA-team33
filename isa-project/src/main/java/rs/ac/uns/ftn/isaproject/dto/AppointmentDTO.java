package rs.ac.uns.ftn.isaproject.dto;

import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfAppointment;

public class AppointmentDTO {
	
	public int id;
	public TypeOfAppointment typeOfAppointment;
	public String startTime;
	public String endTime;
	public double price;
	public int idPharmacy;
	public AppointmentStatus status;
	
	public AppointmentDTO() {}
	
	public AppointmentDTO(int id, TypeOfAppointment typeOfAppointment, String startTime, String endTime, double price,
			int idPharmacy, AppointmentStatus status) {
		super();
		this.id = id;
		this.typeOfAppointment = typeOfAppointment;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.idPharmacy = idPharmacy;
		this.status = status;
	}


}

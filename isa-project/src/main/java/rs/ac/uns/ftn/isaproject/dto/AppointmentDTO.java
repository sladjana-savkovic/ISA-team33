package rs.ac.uns.ftn.isaproject.dto;

import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;

public class AppointmentDTO {
	
	public int id;
	public String startTime;
	public String endTime;
	public double price;
	public int idPharmacy;
	public AppointmentStatus status;
	
	public AppointmentDTO() {}
	
	public AppointmentDTO(int id, String startTime, String endTime, double price,
			int idPharmacy, AppointmentStatus status) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.idPharmacy = idPharmacy;
		this.status = status;
	}


}

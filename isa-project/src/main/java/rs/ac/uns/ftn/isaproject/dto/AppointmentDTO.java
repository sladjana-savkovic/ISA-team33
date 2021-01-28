package rs.ac.uns.ftn.isaproject.dto;

public class AppointmentDTO {
	
	public int id;
	public String typeOfAppointment;
	public String startTime;
	public String endTime;
	public double price;
	//public int idDoctor;
	//public int idPatient;
	public int idPharmacy;
	
	public AppointmentDTO() {}
	
	public AppointmentDTO(int id, String typeOfAppointment, String startTime, String endTime, double price,
			int idPharmacy) {
		super();
		this.id = id;
		this.typeOfAppointment = typeOfAppointment;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		//this.idDoctor = idDoctor;
		//this.idPatient = idPatient;
		this.idPharmacy = idPharmacy;
	}


}

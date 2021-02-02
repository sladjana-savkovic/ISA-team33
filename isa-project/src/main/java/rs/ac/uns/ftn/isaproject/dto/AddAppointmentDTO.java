package rs.ac.uns.ftn.isaproject.dto;


public class AddAppointmentDTO {

	public int id;
	public String startTime;
	public String endTime;
	public int idDoctor;
	public int idPharmacy;
	public double price;
	
	public AddAppointmentDTO() {}
	
	public AddAppointmentDTO(int id, String startTime, String endTime, int idDoctor, int idPharmacy,
			double price) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.idDoctor = idDoctor;
		this.idPharmacy = idPharmacy;
		this.price = price;
	}
	
	
}

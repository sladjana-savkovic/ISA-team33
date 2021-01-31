package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDateTime;

public class AppointmentForExaminationDTO {

	public int appointmentId;
	public int patientId;
	public String patientName;
	public String patientSurname;
	public int doctorId;
	public String doctorName;
	public String doctorSurname;
	public int pharmacyId;
	public String pharmacyName;
	public LocalDateTime startTime;
	public LocalDateTime endTime;
	public double price;
	public String status;
	
	public AppointmentForExaminationDTO() {}

	public AppointmentForExaminationDTO(int appointmentId, int patientId, String patientName, String patientSurname,
			int doctorId, String doctorName, String doctorSurname, int pharmacyId, String pharmacyName,
			LocalDateTime startTime, LocalDateTime endTime, double price,String status) {
		super();
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.patientName = patientName;
		this.patientSurname = patientSurname;
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.doctorSurname = doctorSurname;
		this.pharmacyId = pharmacyId;
		this.pharmacyName = pharmacyName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.status = status;
	}
	
}

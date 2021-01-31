package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalTime;

public class WorkingTimeDTO {

	public int id;
	public int doctorId;
	public int pharmacyId;
	public LocalTime startTime;
	public LocalTime endTime;
	
	public WorkingTimeDTO() {}
	
	public WorkingTimeDTO(int id, int doctorId, int pharmacyId, LocalTime startTime, LocalTime endTime) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.pharmacyId = pharmacyId;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
}

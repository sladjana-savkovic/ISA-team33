package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDateTime;

public class DrugReservationDTO {

	public int id;
	public int drugId;
	public String drugName;
	public LocalDateTime dateLimit;
	public boolean isDone;
	public int patientId;
	public String patientName;
	public String patientSurname;
	
	public DrugReservationDTO() {}

	public DrugReservationDTO(int id, int drugId, String drugName, LocalDateTime dateLimit, boolean isDone,
			int patientId, String patientName, String patientSurname) {
		super();
		this.id = id;
		this.drugId = drugId;
		this.drugName = drugName;
		this.dateLimit = dateLimit;
		this.isDone = isDone;
		this.patientId = patientId;
		this.patientName = patientName;
		this.patientSurname = patientSurname;
	}
	
}

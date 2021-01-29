package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class ViewVacationRequestDTO {

	public int id;
	public int doctorId;
	public String doctorSurname;
	public String typeOfDoctor;
	public LocalDate startDate;
	public LocalDate endDate;
	public int pharmacyId;
	
	public ViewVacationRequestDTO() {}
	
	public ViewVacationRequestDTO(int id, int doctorId, String doctorSurname, String typeOfDoctor, LocalDate startDate,
			LocalDate endDate, int pharmacyId) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.doctorSurname = doctorSurname;
		this.typeOfDoctor = typeOfDoctor;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pharmacyId = pharmacyId;
	}
	
}

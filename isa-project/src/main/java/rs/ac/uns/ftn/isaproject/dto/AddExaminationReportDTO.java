package rs.ac.uns.ftn.isaproject.dto;

import java.util.Collection;

public class AddExaminationReportDTO {

	public int id;
	public int appointmentId;
	public String diagnosis;
	public int pharmacyId;
	public Collection<AddTherapyDTO> therapyDTOs;
	
	public AddExaminationReportDTO() {}

	public AddExaminationReportDTO(int id, int appointmentId, String diagnosis,int pharmacyId, Collection<AddTherapyDTO> therapyDTOs) {
		super();
		this.id = id;
		this.appointmentId = appointmentId;
		this.diagnosis = diagnosis;
		this.pharmacyId = pharmacyId;
		this.therapyDTOs = therapyDTOs;
	}
	 
}

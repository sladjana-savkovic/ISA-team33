package rs.ac.uns.ftn.isaproject.dto;


public class AddExaminationReportDTO {

	public int id;
	public int appointmentId;
	public String diagnosis;
	
	public AddExaminationReportDTO() {}

	public AddExaminationReportDTO(int id, int appointmentId, String diagnosis) {
		super();
		this.id = id;
		this.appointmentId = appointmentId;
		this.diagnosis = diagnosis;
	}
	 
}

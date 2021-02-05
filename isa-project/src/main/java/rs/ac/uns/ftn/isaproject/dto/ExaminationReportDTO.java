package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDateTime;
import java.util.Collection;

public class ExaminationReportDTO {

	public LocalDateTime dateTime;
	public String doctor;
	public String diagnosis;
	public Collection<String> therapies;
	
	public ExaminationReportDTO() {}

	public ExaminationReportDTO(LocalDateTime dateTime, String doctor, String diagnosis, Collection<String> therapies) {
		super();
		this.dateTime = dateTime;
		this.doctor = doctor;
		this.diagnosis = diagnosis;
		this.therapies = therapies;
	}
	
}

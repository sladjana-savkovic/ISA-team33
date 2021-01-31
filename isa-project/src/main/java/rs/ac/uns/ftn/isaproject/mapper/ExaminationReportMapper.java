package rs.ac.uns.ftn.isaproject.mapper;

import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public class ExaminationReportMapper {

	public static AddExaminationReportDTO toAddExaminationReportDTO(ExaminationReport examinationReport) {
		return new AddExaminationReportDTO(examinationReport.getId(), examinationReport.getAppointment().getId(), examinationReport.getDiagnosis());
	}
}

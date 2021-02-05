package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.ExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;
import rs.ac.uns.ftn.isaproject.model.examinations.Therapy;

public class ExaminationReportMapper {

	public static Collection<ExaminationReportDTO> toExaminationReportDTO(Collection<ExaminationReport> examinationReports) {
		Collection<ExaminationReportDTO> reportDTOs = new ArrayList<>();
		Collection<String> therapies;
		
		for(ExaminationReport report:examinationReports) {
			String doctor = report.getAppointment().getDoctor().getName() + " " + report.getAppointment().getDoctor().getSurname();
			therapies = new ArrayList<>();
			
			for(Therapy therapy:report.getTherapies()) {
				therapies.add(therapy.getDrug().getName());
			}
			
			reportDTOs.add(new ExaminationReportDTO(report.getAppointment().getStartTime(), doctor, report.getDiagnosis(),therapies));
		}
		
		return reportDTOs;
	}
}

package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public class ExaminedPatientMapper {

	public static Collection<ExaminedPatientDTO> toExaminedPatientDTOs(Collection<ExaminationReport> examinationReports){
		Collection<ExaminedPatientDTO> examinedPatientDTOs = new ArrayList<>();
		for(ExaminationReport report:examinationReports) {
			examinedPatientDTOs.add(new ExaminedPatientDTO(report.getAppointment().getPatient().getId(),report.getAppointment().getPatient().getName(),
									report.getAppointment().getPatient().getSurname(),report.getAppointment().getStartTime().toLocalDate()));
		}
		return examinedPatientDTOs;
	}
}

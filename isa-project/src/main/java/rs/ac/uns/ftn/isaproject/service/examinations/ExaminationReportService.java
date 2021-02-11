package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.ArrayList;
import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public interface ExaminationReportService {

	ExaminationReport add(AddExaminationReportDTO examinationReportDTO);
	Collection<ExaminationReport> getByPatientAtDoctor(int patientId, int doctorId);
	Collection<ExaminationReportDTO> sortByDate(String sortingType, ArrayList<ExaminationReportDTO> examinationReportDTOs);
}

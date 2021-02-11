package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public interface ExaminationReportService {

	ExaminationReport add(AddExaminationReportDTO examinationReportDTO);
	//Collection<ExaminedPatientDTO> sortByDate(String sortingType, ArrayList<ExaminedPatientDTO> examinedPatientDTOs);
	Collection<ExaminationReport> getByPatientAtDoctor(int patientId, int doctorId);
}

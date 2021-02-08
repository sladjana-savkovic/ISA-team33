package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.ArrayList;
import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public interface ExaminationReportService {

	Collection<ExaminationReport> findAllByDoctorIdAndStatus(int doctorId, int status);
	ExaminationReport add(AddExaminationReportDTO examinationReportDTO);
	Collection<ExaminedPatientDTO> searchByNameAndSurname(String name, String surname,Collection<ExaminedPatientDTO> examinedPatientDTOs);
	Collection<ExaminedPatientDTO> sortByDate(String sortingType, ArrayList<ExaminedPatientDTO> examinedPatientDTOs);
	Collection<ExaminationReport> getByPatientAtDoctor(int patientId, int doctorId);
}

package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public interface ExaminationReportService {

	Collection<ExaminationReport> findAllFinishedByDoctorId(int id);
	ExaminationReport add(AddExaminationReportDTO examinationReportDTO);
	Collection<ExaminedPatientDTO> searchByNameAndSurname(String name, String surname,Collection<ExaminedPatientDTO> examinedPatientDTOs);
}

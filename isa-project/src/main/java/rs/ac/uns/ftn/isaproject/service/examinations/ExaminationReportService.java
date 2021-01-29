package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public interface ExaminationReportService {

	Collection<ExaminationReport> findAllByDoctorId(int id);
	void add(AddExaminationReportDTO examinationReportDTO);
}

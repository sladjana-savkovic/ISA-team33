package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public interface ExaminationReportService {

	Collection<ExaminationReport> findAllByDoctorId(int id);
}

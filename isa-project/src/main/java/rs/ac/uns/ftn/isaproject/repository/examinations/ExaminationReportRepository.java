package rs.ac.uns.ftn.isaproject.repository.examinations;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public interface ExaminationReportRepository extends JpaRepository<ExaminationReport, Integer> {

}

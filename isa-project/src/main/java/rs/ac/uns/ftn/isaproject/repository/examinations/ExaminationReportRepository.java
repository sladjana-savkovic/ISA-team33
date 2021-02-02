package rs.ac.uns.ftn.isaproject.repository.examinations;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public interface ExaminationReportRepository extends JpaRepository<ExaminationReport, Integer> {

	@Query(value = "select * from examination_report er, appointment ap where er.appointment_id = ap.id and ap.doctor_id = ?1 and ap.status = 3 order by ap.start_time desc", nativeQuery = true)
	Collection<ExaminationReport> findAllFinishedByDoctorId(int id);
	
}

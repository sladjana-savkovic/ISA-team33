package rs.ac.uns.ftn.isaproject.repository.examinations;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;

public interface ExaminationReportRepository extends JpaRepository<ExaminationReport, Integer> {
	
	@Query(value = "select * from examination_report er, appointment ap, doctor d where "
			+ "er.appointment_id = ap.id and ap.doctor_id = d.id and ap.patient_id = ?1 and d.type_of_doctor = "
			+ "(select type_of_doctor from doctor d1 where d1.id = ?2)", nativeQuery = true)
	Collection<ExaminationReport> getByPatientAtDoctor(int patientId, int doctorId);
	
}

package rs.ac.uns.ftn.isaproject.repository.users;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.users.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

	@Query(value = "select * from patient p, appointment a, examination_report er where "
				 + "p.id = a.patient_id and a.doctor_id = ?1 and a.id = er.appointment_id", nativeQuery = true)
	Collection<Patient> findExaminedPatientsByDoctorId(int doctorId);
	
	@Query(value = "select * from patient p, appointment a where p.id = a.patient_id and a.doctor_id = ?1", nativeQuery = true)
	Collection<Patient> findPatientsHaveAppointmentByDoctorId(int doctorId);
}

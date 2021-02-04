package rs.ac.uns.ftn.isaproject.repository.examinations;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	@Query(value = "select * from appointment a where a.doctor_id=?1", nativeQuery = true)
	Collection<Appointment> getDoctorAppointments(int doctorId);
	
	@Query(value = "select * from appointment a where a.pharmacy_id=?1 and a.doctor_id=?2 and (a.status = 0 or a.status = 2)", nativeQuery = true)
	Collection<Appointment> findFreeAppointmentsByPharmacyAndDoctor(int pharmacyId, int doctorId);
	
	@Query(value = "select * from appointment a where a.doctor_id=?1 and a.pharmacy_id=?2", nativeQuery = true)
	Collection<Appointment> getDoctorAppointmentsInPharamacy(int doctorId, int pharmacyId);

	@Query(value = "select * from appointment a where a.patient_id=?1 and a.start_time=?2", nativeQuery = true)
	Collection<Appointment> checkIfPatientHasAppointment(int patientId, LocalDateTime startTime);

	@Query(value = "select * from appointment a where a.pharmacy_id=?1 and a.status = 0", nativeQuery = true)
	Collection<Appointment> findAllCreatedByPharmacy(int pharmacyId);
	
	@Query(value = "select * from appointment a where a.patient_id=?1", nativeQuery = true)
	Collection<Appointment> getPatientAppointments(int patientId);
	
	Collection<Appointment> findAllByPatientIdAndDoctorTypeOfDoctorAndStatus(int patientId,TypeOfDoctor type, AppointmentStatus status);
	
}

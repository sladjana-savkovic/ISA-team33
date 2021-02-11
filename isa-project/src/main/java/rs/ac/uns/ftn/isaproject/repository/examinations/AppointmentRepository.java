package rs.ac.uns.ftn.isaproject.repository.examinations;

import java.util.Collection;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	@Query(value = "select * from appointment a where a.doctor_id=?1 and (a.status = 0 or a.status = 1 or a.status = 3 or a.status = 4)", nativeQuery = true)
	Collection<Appointment> getDoctorAppointments(int doctorId);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from Appointment a join a.doctor d where d.id = :doctorId and (a.status = 0 or a.status = 1)")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	Collection<Appointment> getUnavailableDoctorAppointments(@Param("doctorId")int doctorId);
	
	@Query(value = "select * from appointment a where a.pharmacy_id=?1 and a.doctor_id=?2 and a.status = 0", nativeQuery = true)
	Collection<Appointment> findFreeAppointmentsByPharmacyAndDoctor(int pharmacyId, int doctorId);
	
	@Query(value = "select * from appointment a where a.doctor_id=?1 and a.pharmacy_id=?2", nativeQuery = true)
	Collection<Appointment> getDoctorAppointmentsInPharamacy(int doctorId, int pharmacyId);

	@Query(value = "select * from appointment a where a.pharmacy_id=?1 and a.status = 0", nativeQuery = true)
	Collection<Appointment> findAllCreatedByPharmacy(int pharmacyId);
	
	@Query(value = "select * from appointment a where a.patient_id=?1", nativeQuery = true)
	Collection<Appointment> getPatientAppointments(int patientId);
	
	@Query(value = "select * from appointment a where a.patient_id=?1 and a.status = 1", nativeQuery = true)
	Collection<Appointment> getScheduledPatientAppointments(int patientId);

	Collection<Appointment> findAllByPatientIdAndDoctorTypeOfDoctorAndStatus(int patientId,TypeOfDoctor type, AppointmentStatus status);

	Collection<Appointment> findAllByDoctorTypeOfDoctorAndPharmacyId(TypeOfDoctor type, int id);

	@Query(value = "select * from appointment a where a.doctor_id=?1 and a.status = 1", nativeQuery = true)
	Collection<Appointment> getScheduledAppointmentsByDoctor(int doctorId);
	
	@Query(value = "select * from Appointment a where a.pharmacy_id=?1 and a.status = 3", nativeQuery = true)
	Collection<Appointment> getPerformedAppointmentsInPharamacy(int pharmacyId);
	
	@Query(value = "select * from Appointment a where a.patient_id=?1 and a.doctor_id=?2 and a.status = 1", nativeQuery = true)
	Collection<Appointment> getPatientsScheduledAppointmentsByDoctor(int patientId, int doctorId);

	@Query(value = "select count(*) from appointment a where a.patient_id=?1 and a.doctor_id=?2 and a.status = 3", nativeQuery = true)
	int getFinishedAppointmentsByPatientAndDoctor(int patientId, int doctorId);
	
	@Query(value = "select count(*) from appointment a where a.patient_id=?1 and a.pharmacy_id=?2 and a.status = 3", nativeQuery = true)
	int getNumberOfFinishedAppointmentsByPatientAndPharmacy(int patientId, int pharmacyId);
	
}

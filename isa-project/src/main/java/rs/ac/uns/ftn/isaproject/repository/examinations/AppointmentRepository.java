package rs.ac.uns.ftn.isaproject.repository.examinations;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	@Query(value = "select * from appointment a where a.doctor_id=?1", nativeQuery = true)
	Collection<Appointment> getDoctorAppointments(int id);
	
	@Query(value = "select * from appointment a where a.pharmacy_id=?1 and a.doctor_id=?2 and a.status = 0", nativeQuery = true)
	Collection<Appointment> findAllCreatedByPharmacyAndDoctor(int pharmacyId, int doctorId);
}

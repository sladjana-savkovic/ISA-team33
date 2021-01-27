package rs.ac.uns.ftn.isaproject.repository.examinations;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

}

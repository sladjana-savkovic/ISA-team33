package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public interface AppointmentService {

	void changeStatus(int id, AppointmentStatus status);
	Appointment getOne(int id);
	Collection<Appointment> getDoctorAppointments(int id);
}

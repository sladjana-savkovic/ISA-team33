package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public interface AppointmentService {

	void changeStatus(int id, AppointmentStatus status) throws Exception;
	Appointment getOne(int id);
	Collection<Appointment> getDoctorAppointments(int id);
	Collection<Appointment> findAllCreatedByPharmacyAndDoctor(int pharmacyId, int doctorId);
	void scheduleAppointment(int id, int patientId) throws BadRequestException;
	Collection<AppointmentDTO> searchByStartTime(String startTime, Collection<AppointmentDTO> appointmentDTOs);
	Collection<Appointment> getDoctorScheduledAppointmentsInPharamacy(int doctorId, int pharmacyId);
	Collection<Appointment> findAllCreatedByPharmacy(int pharmacyId);
}

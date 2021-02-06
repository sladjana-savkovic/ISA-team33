package rs.ac.uns.ftn.isaproject.service.examinations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public interface AppointmentService {

	void changeStatus(int id, AppointmentStatus status) throws Exception;
	Appointment getOne(int id);
	Collection<Appointment> getDoctorAppointments(int id);
	Collection<Appointment> findFreeAppointmentsByPharmacyAndDoctor(int pharmacyId, int doctorId);
	void schedulePredefinedAppointment(int id, int patientId) throws Exception;
	Collection<AppointmentDTO> searchByStartTime(String startTime, Collection<AppointmentDTO> appointmentDTOs);
	Collection<Appointment> getDoctorScheduledAppointmentsInPharamacy(int doctorId, int pharmacyId);
	Collection<Appointment> findAllCreatedByPharmacyDermatologist(int pharmacyId);
	Collection<Appointment> findAllCreatedByPharmacy(int pharmacyId);
	boolean isDoctorAvailableForChosenTime(int doctorId, LocalDate date, LocalTime startTime, LocalTime endTime);
	boolean isPatientAvailableForChosenTime(int patientId, LocalDate date, LocalTime startTime, LocalTime endTime);
	void add(AddAppointmentDTO appointmentDTO, AppointmentStatus status);
	Collection<Appointment> getPatientsScheduledAppointmentsDoctor(int patientId, TypeOfDoctor doctorType);
	void cancelAppointment(int id) throws Exception;
	void save(Appointment appointment);
	public Collection<Appointment> getCreatedAndScheduledDoctorAppointments(int doctorId);
	void checkDoctorAvailabilityAndAddAppointment(int doctorId, LocalDate date, LocalTime startTime, LocalTime endTime,
												 AddAppointmentDTO appointmentDTO, AppointmentStatus status)  throws Exception;
}

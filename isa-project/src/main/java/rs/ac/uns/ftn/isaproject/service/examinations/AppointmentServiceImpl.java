package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.model.users.Patient;
import rs.ac.uns.ftn.isaproject.repository.examinations.AppointmentRepository;
import rs.ac.uns.ftn.isaproject.repository.users.PatientRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	private AppointmentRepository appointmentRepository;
	private PatientRepository patientRepository;
	
	@Autowired
	public AppointmentServiceImpl(AppointmentRepository appointmentRepository,PatientRepository patientRepository) {
		this.appointmentRepository = appointmentRepository;
		this.patientRepository = patientRepository;
	}

	@Override
	public void changeStatus(int id, AppointmentStatus status) throws Exception {
		Appointment appointment = appointmentRepository.getOne(id);
		
		if(appointment.getStatus() == status)
			throw new BadRequestException("The appointment already has a changed status.");
		
		appointment.setStatus(status);
		appointmentRepository.save(appointment);
	}

	@Override
	public Appointment getOne(int id) {
		return appointmentRepository.getOne(id);
	}

	@Override
	public Collection<Appointment> getDoctorAppointments(int id) {
		return appointmentRepository.getDoctorAppointments(id);
	}

	@Override
	public Collection<Appointment> findAllCreatedByPharmacyAndDoctor(int pharmacyId, int doctorId) {
		return appointmentRepository.findAllCreatedByPharmacyAndDoctor(pharmacyId, doctorId);
	}

	@Override
	public void schedulePredefinedAppointment(int id, int patientId) throws BadRequestException {
		Appointment appointment = appointmentRepository.getOne(id);
		
		if(appointment.getStatus() == AppointmentStatus.Scheduled) 
			throw new BadRequestException("An appointment has already been scheduled.");
		
		Patient patient = patientRepository.getOne(patientId);
		appointment.setPatient(patient);
		appointment.setStatus(AppointmentStatus.Scheduled);
		
		appointmentRepository.save(appointment);
	}

	@Override
	public Collection<AppointmentDTO> searchByStartTime(String startTime, Collection<AppointmentDTO> appointmentDTOs) {
		Collection<AppointmentDTO> searchResult = new ArrayList<>();
		
		for(AppointmentDTO dto:appointmentDTOs) {
			if(dto.startTime.contains(startTime)){
				searchResult.add(dto);
			}
		}
		
		return searchResult;
	}

	public Collection<Appointment> getDoctorScheduledAppointmentsInPharamacy(int doctorId, int pharmacyId) {
		Collection<Appointment> appointments = appointmentRepository.getDoctorAppointmentsInPharamacy(doctorId, pharmacyId);
		Collection<Appointment> resultAppointments = new ArrayList<Appointment>();
		for(Appointment a : appointments) {
			if(a.getStatus() == AppointmentStatus.Scheduled) {
				resultAppointments.add(a);
			}
		}
		return resultAppointments;
	}

	@Override
	public Collection<Appointment> findAllCreatedByPharmacy(int pharmacyId) {
		return appointmentRepository.findAllCreatedByPharmacy(pharmacyId);
	}
}

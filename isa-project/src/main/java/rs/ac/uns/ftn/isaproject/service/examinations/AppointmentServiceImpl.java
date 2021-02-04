package rs.ac.uns.ftn.isaproject.service.examinations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.model.users.Patient;
import rs.ac.uns.ftn.isaproject.repository.examinations.AppointmentRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;
import rs.ac.uns.ftn.isaproject.repository.users.PatientRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	private AppointmentRepository appointmentRepository;
	private PatientRepository patientRepository;
	private DoctorRepository doctorRepository;
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	public AppointmentServiceImpl(AppointmentRepository appointmentRepository,PatientRepository patientRepository,DoctorRepository doctorRepository,PharmacyRepository pharmacyRepository) {
		this.appointmentRepository = appointmentRepository;
		this.patientRepository = patientRepository;
		this.doctorRepository = doctorRepository;
		this.pharmacyRepository = pharmacyRepository;
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
	public Collection<Appointment> findFreeAppointmentsByPharmacyAndDoctor(int pharmacyId, int doctorId) {
		return appointmentRepository.findFreeAppointmentsByPharmacyAndDoctor(pharmacyId, doctorId);
	}

	@Override
	public void schedulePredefinedAppointment(int id, int patientId) throws BadRequestException {
		Appointment appointment = appointmentRepository.getOne(id);
		Collection<Appointment> appointments = appointmentRepository.checkIfPatientHasAppointment(patientId, appointment.getStartTime());
		
		if(appointment.getStatus() == AppointmentStatus.Scheduled) 
			throw new BadRequestException("An appointment has already been scheduled.");
		
		if(appointments.size() > 0) {
			throw new BadRequestException("The patient is busy for the chosen appointment.");
		}
		
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


	@Override
	public boolean isDoctorAvailableForChosenTime(int doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
		Collection<Appointment> doctorAppointments = appointmentRepository.getDoctorAppointments(doctorId);
		return !checkIfAppointmentMathces(doctorAppointments, date, startTime, endTime);
	}

	@Override
	public boolean isPatientAvailableForChosenTime(int patientId, LocalDate date, LocalTime startTime, LocalTime endTime) {
		Collection<Appointment> patientAppointments = appointmentRepository.getPatientAppointments(patientId);
		return !checkIfAppointmentMathces(patientAppointments, date, startTime, endTime);
	}
	
	private boolean checkIfAppointmentMathces(Collection<Appointment> appointments, LocalDate date, LocalTime startTime, LocalTime endTime) {
		for(Appointment a : appointments) {
			if(date.equals(a.getStartTime().toLocalDate()) && 
			 (a.getStatus().equals(AppointmentStatus.Created) || a.getStatus().equals(AppointmentStatus.Scheduled)) && 
			 ((startTime.equals(a.getStartTime().toLocalTime()) && endTime.equals(a.getEndTime().toLocalTime())) || 
			  (startTime.isAfter(a.getStartTime().toLocalTime()) && endTime.isBefore(a.getEndTime().toLocalTime()))  || 
			  (startTime.isBefore(a.getStartTime().toLocalTime()) && endTime.isAfter(a.getEndTime().toLocalTime())) || 
			  (startTime.isAfter(a.getStartTime().toLocalTime()) && startTime.isBefore(a.getEndTime().toLocalTime())) || 
			  (startTime.isBefore(a.getStartTime().toLocalTime()) && endTime.isAfter(a.getStartTime().toLocalTime())))) {
					return true;
			}
		}
		return false;
	}

	@Override
	public void add(AddAppointmentDTO appointmentDTO, AppointmentStatus status) {
		Appointment appointment = new Appointment();
		Doctor doctor = doctorRepository.getOne(appointmentDTO.idDoctor);
		Pharmacy pharmacy = pharmacyRepository.getOne(appointmentDTO.idPharmacy);
		
		appointment.setStartTime(LocalDateTime.parse(appointmentDTO.startTime));
		appointment.setEndTime(LocalDateTime.parse(appointmentDTO.endTime));
		appointment.setPrice(appointmentDTO.price);
		appointment.setStatus(status);
		appointment.setDoctor(doctor);
		appointment.setPharmacy(pharmacy);
		
		if(status == AppointmentStatus.Scheduled) {
			Patient patient = patientRepository.getOne(appointmentDTO.idPatient);
			appointment.setPatient(patient);
		}
		
		appointmentRepository.save(appointment);
	}

	@Override
	public Collection<Appointment> getPatientsScheduledAppointmentsDoctor(int patientId, TypeOfDoctor doctorType) {
		Collection<Appointment> appointments = appointmentRepository.findAllByPatientIdAndDoctorTypeOfDoctorAndStatus(patientId,doctorType,AppointmentStatus.Scheduled);
		return appointments;
	}
	@Override
	public void cancelAppointment(int id) throws Exception {
		Appointment appointment = appointmentRepository.findById(id).get();
		
		if(appointment.getStatus() != AppointmentStatus.Scheduled || !appointment.getStartTime().isAfter(LocalDateTime.now().plus(Period.ofDays(1))))
			throw new BadRequestException("The appointment cannot be cancelled.");
		
		appointment.setStatus(AppointmentStatus.Canceled);
		appointment.setPatient(null);
		appointmentRepository.save(appointment);
	}
	
}

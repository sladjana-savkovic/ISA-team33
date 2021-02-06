package rs.ac.uns.ftn.isaproject.service.examinations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
	@Transactional(readOnly = false,propagation = Propagation.SUPPORTS)
	public void changeStatus(int id, AppointmentStatus status) throws Exception {
		Appointment appointment = appointmentRepository.getOne(id);

		if (appointment.getStatus() == status)
			throw new BadRequestException("The appointment already has a changed status.");

		appointment.setStatus(status);
		appointmentRepository.save(appointment);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Appointment getOne(int id ) {
		return appointmentRepository.getOne(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Collection<Appointment> getDoctorAppointments(int id) {
		return appointmentRepository.getDoctorAppointments(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Collection<Appointment> findFreeAppointmentsByPharmacyAndDoctor(int pharmacyId, int doctorId) {
		return appointmentRepository.findFreeAppointmentsByPharmacyAndDoctor(pharmacyId, doctorId);
	}

	@Override
	@Transactional(readOnly = false,  propagation = Propagation.REQUIRES_NEW)
	public void schedulePredefinedAppointment(int id, int patientId) throws Exception {
		logger.info("> schedule id:{}", id);
		
		Appointment appointment = getOne(id);
		if(appointment.getStatus() == AppointmentStatus.Scheduled) 
			throw new BadRequestException("An appointment has already been scheduled.");
		
		Collection<Appointment> appointments = appointmentRepository.checkIfPatientHasScheduledAppointment(patientId, appointment.getStartTime());
		if(appointments.size() > 0) {
			throw new BadRequestException("The patient is busy for the chosen appointment.");
		}
		
		Patient patient = patientRepository.getOne(patientId);
		appointment.setPatient(patient);
		appointment.setStatus(AppointmentStatus.Scheduled);
		save(appointment);
		
		logger.info("< schedule id:{}", id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Collection<AppointmentDTO> searchByStartTime(String startTime, Collection<AppointmentDTO> appointmentDTOs) {
		Collection<AppointmentDTO> searchResult = new ArrayList<>();

		for (AppointmentDTO dto : appointmentDTOs) {
			if (dto.startTime.contains(startTime)) {
				searchResult.add(dto);
			}
		}

		return searchResult;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Collection<Appointment> getDoctorScheduledAppointmentsInPharamacy(int doctorId, int pharmacyId) {
		Collection<Appointment> appointments = appointmentRepository.getDoctorAppointmentsInPharamacy(doctorId,
				pharmacyId);
		Collection<Appointment> resultAppointments = new ArrayList<Appointment>();
		for (Appointment a : appointments) {
			if (a.getStatus() == AppointmentStatus.Scheduled) {
				resultAppointments.add(a);
			}
		}
		return resultAppointments;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Collection<Appointment> findAllCreatedByPharmacyDermatologist(int pharmacyId) {
		Collection<Appointment> appointments = appointmentRepository.findAllByDoctorTypeOfDoctorAndPharmacyId(TypeOfDoctor.Dermatologist, pharmacyId);
		Collection<Appointment> resultAppointments = new ArrayList<Appointment>();
		for (Appointment a : appointments) {
			if (a.getStatus() == AppointmentStatus.Canceled || a.getStatus() == AppointmentStatus.Created) {
				resultAppointments.add(a);
			}
		}
		return resultAppointments;
	}
	
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Collection<Appointment> findAllCreatedByPharmacy(int pharmacyId) {
		return appointmentRepository.findAllCreatedByPharmacy(pharmacyId);
	}


	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean isDoctorAvailableForChosenTime(int doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
		Collection<Appointment> doctorAppointments = appointmentRepository.getCreatedAndScheduledDoctorAppointments(doctorId);
		return !checkIfAppointmentMathces(doctorAppointments, date, startTime, endTime);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean isPatientAvailableForChosenTime(int patientId, LocalDate date, LocalTime startTime, LocalTime endTime) {
		Collection<Appointment> patientAppointments = appointmentRepository.getScheduledPatientAppointments(patientId);
		return !checkIfAppointmentMathces(patientAppointments, date, startTime, endTime);
	}


	@Override
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
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
			appointment.setPrice(pharmacy.getPharmacistPrice());
		}
		
		appointmentRepository.save(appointment);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Collection<Appointment> getPatientsScheduledAppointmentsDoctor(int patientId, TypeOfDoctor doctorType) {
		Collection<Appointment> appointments = appointmentRepository.findAllByPatientIdAndDoctorTypeOfDoctorAndStatus(patientId,doctorType,AppointmentStatus.Scheduled);
		return appointments;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
	public void cancelAppointment(int id) throws Exception {
		Appointment appointment = appointmentRepository.findById(id).get();
		
		if(appointment.getStatus() != AppointmentStatus.Scheduled || !appointment.getStartTime().isAfter(LocalDateTime.now().plus(Period.ofDays(1))))
			throw new BadRequestException("The appointment cannot be cancelled.");
		
		appointment.setStatus(AppointmentStatus.Canceled);
		appointment.setPatient(null);
		appointmentRepository.save(appointment);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.SUPPORTS)
	public void save(Appointment appointment) {
		logger.info("> save");
		appointmentRepository.save(appointment);		
		logger.info("< save");
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
}

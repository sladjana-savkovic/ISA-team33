package rs.ac.uns.ftn.isaproject.service.examinations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
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
	
	//Metoda vraca true ukoliko se slobodan termin pregleda kod zadatog doktora za zadato vrijeme i datum moze kreirati, u suprotnom vraca false
	@Override
	public boolean isAppointmentAvailableToCreate(int doctor_id, String date, String start_time, String end_time) {
		String date_parse = date.split(" ")[0];
		String start_time_parse = start_time.split(" ")[1];
		String end_time_parse = end_time.split(" ")[1];
		Collection<Appointment> appointments = appointmentRepository.getDoctorAppointments(doctor_id);
		for(Appointment a : appointments) {
			if(LocalDate.parse(date_parse).equals(a.getStartTime().toLocalDate()) && (a.getStatus().equals(AppointmentStatus.Created) || a.getStatus().equals(AppointmentStatus.Scheduled)) && ((LocalTime.parse(start_time_parse).equals(a.getStartTime().toLocalTime()) && LocalTime.parse(end_time_parse).equals(a.getEndTime().toLocalTime())) || (LocalTime.parse(start_time_parse).isAfter(a.getStartTime().toLocalTime()) && LocalTime.parse(end_time_parse).isBefore(a.getEndTime().toLocalTime()))  || (LocalTime.parse(start_time_parse).isBefore(a.getStartTime().toLocalTime()) && LocalTime.parse(end_time_parse).isAfter(a.getEndTime().toLocalTime())) || (LocalTime.parse(start_time_parse).isAfter(a.getStartTime().toLocalTime()) && LocalTime.parse(start_time_parse).isBefore(a.getEndTime().toLocalTime())) || (LocalTime.parse(start_time_parse).isBefore(a.getStartTime().toLocalTime()) && LocalTime.parse(end_time_parse).isAfter(a.getStartTime().toLocalTime())) ) ) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void add(AddAppointmentDTO appointmentDTO) {
		Appointment appointment = new Appointment();
		Doctor doctor = doctorRepository.getOne(appointmentDTO.idDoctor);
		Pharmacy pharmacy = pharmacyRepository.getOne(appointmentDTO.idPharmacy);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		LocalDateTime startDateTime = LocalDateTime.parse(appointmentDTO.startTime, formatter);
		LocalDateTime endDateTime = LocalDateTime.parse(appointmentDTO.endTime, formatter);
		
		appointment.setStartTime(startDateTime);
		appointment.setEndTime(endDateTime);
		appointment.setPrice(appointmentDTO.price);
		appointment.setStatus(AppointmentStatus.Created);
		appointment.setDoctor(doctor);
		appointment.setPharmacy(pharmacy);
		
		appointmentRepository.save(appointment);
	}
}

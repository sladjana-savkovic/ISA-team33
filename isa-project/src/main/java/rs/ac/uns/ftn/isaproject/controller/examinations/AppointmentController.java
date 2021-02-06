package rs.ac.uns.ftn.isaproject.controller.examinations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.LockTimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentEventDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentForExaminationDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.mapper.AppointmentEventMapper;
import rs.ac.uns.ftn.isaproject.mapper.AppointmentMapper;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.utils.GradeComparator;
import rs.ac.uns.ftn.isaproject.model.utils.Order;
import rs.ac.uns.ftn.isaproject.model.utils.PriceComparator;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;
import rs.ac.uns.ftn.isaproject.service.users.PatientService;
import rs.ac.uns.ftn.isaproject.service.users.VacationRequestService;
import rs.ac.uns.ftn.isaproject.service.users.WorkingTimeService;

@RestController
@RequestMapping(value = "api/appointment")
public class AppointmentController {

	private AppointmentService appointmentService;
	private VacationRequestService vacationRequestService;
	private WorkingTimeService workingTimeService;
	private PatientService patientService;
	
	@Autowired
	public AppointmentController(AppointmentService appointmentService, VacationRequestService vacationRequestService, WorkingTimeService workingTimeService,
								 PatientService patientService) {
		this.appointmentService = appointmentService;
		this.vacationRequestService = vacationRequestService;
		this.workingTimeService = workingTimeService;
		this.patientService = patientService;
	}
	
	@PutMapping("/{id}/patient/{patientId}/unperformed")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> changeStatusToUnperformed(@PathVariable int id, @PathVariable int patientId){
		try {
			appointmentService.changeStatus(id, AppointmentStatus.Unperformed);
			patientService.increasePenalty(patientId);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e) {
			return new ResponseEntity<>("An error occurred while changing appointment status to unperformed.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<AppointmentForExaminationDTO> getOne(@PathVariable int id){
		try {
			AppointmentForExaminationDTO appointmentForExaminationDTO = AppointmentMapper.toAppointmentForExaminationDTO(appointmentService.getOne(id));
			return new ResponseEntity<AppointmentForExaminationDTO>(appointmentForExaminationDTO,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/doctor/{id}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> getDoctorAppointments(@PathVariable int id){
		try {
			Collection<AppointmentEventDTO> appointmentEventDTOs = AppointmentEventMapper.toAppointmentEventDTOs(appointmentService.getDoctorAppointments(id));
			return new ResponseEntity<Collection<AppointmentEventDTO>>(appointmentEventDTOs,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("The requested doctor's appointment doesn't exist in the database.", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("pharmacy/{pharmacyId}/doctor/{doctorId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Collection<AppointmentDTO>> findFreeAppointmentsByPharmacyAndDoctor(@PathVariable int pharmacyId, @PathVariable int doctorId){
		Collection<AppointmentDTO> appointmentDTOs = AppointmentMapper.toAppointmentDTOs(appointmentService.findFreeAppointmentsByPharmacyAndDoctor(pharmacyId, doctorId));
		return new ResponseEntity<Collection<AppointmentDTO>>(appointmentDTOs,HttpStatus.OK);
	}
	
	@PutMapping("{id}/patient/{patientId}/schedule")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PATIENT', 'PHARMACIST')")
	public ResponseEntity<?> schedulePredefinedAppointment(@PathVariable int id, @PathVariable int patientId){
		try {
			appointmentService.schedulePredefinedAppointment(id, patientId);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (BadRequestException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (ObjectOptimisticLockingFailureException e) {
			return new ResponseEntity<>("Someone has scheduled an appointment. Please choose another one.", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while scheduling an appointment.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/search/{startTime}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Collection<AppointmentDTO>> searchByStartTime(@PathVariable String startTime ,@RequestBody ArrayList<AppointmentDTO> appointmentDTOs){
		Collection<AppointmentDTO> searchResult = appointmentService.searchByStartTime(startTime, appointmentDTOs);
		return new ResponseEntity<Collection<AppointmentDTO>>(searchResult, HttpStatus.OK);
	}
	
	@GetMapping("/doctor/{id_doctor}/pharmacy/{id_pharmacy}/scheduled")
	public ResponseEntity<Collection<AppointmentEventDTO>> getDoctorScheduledAppointmentsInPharamacy(@PathVariable int id_doctor, @PathVariable int id_pharmacy){
		try {
			Collection<AppointmentEventDTO> appointmentEventDTOs = AppointmentEventMapper.toAppointmentEventDTOs(appointmentService.getDoctorScheduledAppointmentsInPharamacy(id_doctor, id_pharmacy));
			return new ResponseEntity<Collection<AppointmentEventDTO>>(appointmentEventDTOs,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

	@GetMapping("/pharmacy/{pharmacyId}/created/{sort}")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<Collection<AddAppointmentDTO>> findAllCreatedByPharmacyDermatologist(@PathVariable int pharmacyId,@PathVariable String sort){
		try {
		Collection<AddAppointmentDTO> appointmentDTOs = 
				AppointmentMapper.toAddAppointmentDTOs(appointmentService.findAllCreatedByPharmacyDermatologist(pharmacyId));
		switch (sort) {
		case "GRADE_ASC":
			((List<AddAppointmentDTO>) appointmentDTOs).sort(new GradeComparator(Order.ASC));
			break;

		case "GRADE_DESC":
			((List<AddAppointmentDTO>) appointmentDTOs).sort(new GradeComparator(Order.DESC));
			break;

		case "PRICE_ASC":
			((List<AddAppointmentDTO>) appointmentDTOs).sort(new PriceComparator(Order.ASC));
			break;

		case "PRICE_DESC":
			((List<AddAppointmentDTO>) appointmentDTOs).sort(new PriceComparator(Order.DESC));
			break;
		}
		return new ResponseEntity<Collection<AddAppointmentDTO>>(appointmentDTOs,HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@PostMapping(value="/create", consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> createFreeAppointment(@RequestBody AddAppointmentDTO appointmentDTO) {
		try {
			LocalDate date = LocalDateTime.parse(appointmentDTO.startTime).toLocalDate();
			LocalTime startTime = LocalDateTime.parse(appointmentDTO.startTime).toLocalTime();
			LocalTime endTime = LocalDateTime.parse(appointmentDTO.endTime).toLocalTime();
			
			if(vacationRequestService.isDoctorOnVacation(appointmentDTO.idDoctor,appointmentDTO.idPharmacy, date)) {
				return new ResponseEntity<>("The doctor is on vacation at a chosen time.", HttpStatus.BAD_REQUEST);
			}
			if(!workingTimeService.checkIfDoctorWorkInPharmacy(appointmentDTO.idPharmacy, appointmentDTO.idDoctor, startTime, endTime)) {
					return new ResponseEntity<>("The doctor doesn't work in the pharmacy for the chosen time.",HttpStatus.BAD_REQUEST);
			}
			if(!appointmentService.isDoctorAvailableForChosenTime(appointmentDTO.idDoctor, date, startTime, endTime)) {
				return new ResponseEntity<>("The doctor is busy for a chosen time.", HttpStatus.BAD_REQUEST);
			}
			
			appointmentService.add(appointmentDTO, AppointmentStatus.Created);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating an appointment.", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping(value="/schedule", consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> createAndScheduleAppointment(@RequestBody AddAppointmentDTO appointmentDTO){
		try {
			LocalDate date = LocalDateTime.parse(appointmentDTO.startTime).toLocalDate();
			LocalTime startTime = LocalDateTime.parse(appointmentDTO.startTime).toLocalTime();
			LocalTime endTime = LocalDateTime.parse(appointmentDTO.endTime).toLocalTime();
			
			if(vacationRequestService.isDoctorOnVacation(appointmentDTO.idDoctor,appointmentDTO.idPharmacy, date)) {
				return new ResponseEntity<>("The doctor is on vacation at a chosen time.", HttpStatus.BAD_REQUEST);
			}
			if(!workingTimeService.checkIfDoctorWorkInPharmacy(appointmentDTO.idPharmacy, appointmentDTO.idDoctor, startTime, endTime)) {
					return new ResponseEntity<>("The doctor doesn't work in the pharmacy for the chosen time.",HttpStatus.BAD_REQUEST);
			}
			if(!appointmentService.isDoctorAvailableForChosenTime(appointmentDTO.idDoctor, date, startTime, endTime)) {
				return new ResponseEntity<>("The doctor is busy for a chosen time.", HttpStatus.BAD_REQUEST);
			}
			if(!appointmentService.isPatientAvailableForChosenTime(appointmentDTO.idPatient, date, startTime, endTime)) {
				return new ResponseEntity<>("The patient is busy for a chosen time.", HttpStatus.BAD_REQUEST);
			}
			
			appointmentService.add(appointmentDTO, AppointmentStatus.Scheduled);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (LockTimeoutException  e) {
			return new ResponseEntity<>("Your request has waited too long to be executed. Please try again.", HttpStatus.BAD_REQUEST);
		}
		catch (PessimisticLockingFailureException e) {
			return new ResponseEntity<>("The execution of another user's request is in progress. Please try again.", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while scheduling an appointment.", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/patient/{id_patient}/{doctorType}/scheduled")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<Collection<AddAppointmentDTO>> getPatientsScheduledAppointmentsDoctor(@PathVariable int id_patient,@PathVariable String doctorType){
		try {
			TypeOfDoctor type = doctorType.equals("dermatologists") ? TypeOfDoctor.Dermatologist : TypeOfDoctor.Pharmacist;
			Collection<AddAppointmentDTO> appointmentDTOs = AppointmentMapper.toAddAppointmentDTOs(appointmentService.getPatientsScheduledAppointmentsDoctor(id_patient, type));
			return new ResponseEntity<Collection<AddAppointmentDTO>>(appointmentDTOs,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/{id}/cancel")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<?> cancelAppointment(@PathVariable int id){
		try {
			appointmentService.cancelAppointment(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred while changing appointment status to cancelled.", HttpStatus.BAD_REQUEST);
		}
	}
	
}
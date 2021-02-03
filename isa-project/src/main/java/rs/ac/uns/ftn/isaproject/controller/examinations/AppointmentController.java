package rs.ac.uns.ftn.isaproject.controller.examinations;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import rs.ac.uns.ftn.isaproject.mapper.AppointmentEventDTOMapper;
import rs.ac.uns.ftn.isaproject.mapper.AppointmentMapper;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
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
	public ResponseEntity<?> getDoctorAppointments(@PathVariable int id){
		try {
			Collection<AppointmentEventDTO> appointmentEventDTOs = AppointmentEventDTOMapper.toAppointmentEventDTOs(appointmentService.getDoctorAppointments(id));
			return new ResponseEntity<Collection<AppointmentEventDTO>>(appointmentEventDTOs,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("The requested doctor's appointment doesn't exist in the database.", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("pharmacy/{pharmacyId}/doctor/{doctorId}")
	public ResponseEntity<Collection<AppointmentDTO>> findFreeAppointmentsByPharmacyAndDoctor(@PathVariable int pharmacyId, @PathVariable int doctorId){
		Collection<AppointmentDTO> appointmentDTOs = AppointmentMapper.toAppointmentDTOs(appointmentService.findFreeAppointmentsByPharmacyAndDoctor(pharmacyId, doctorId));
		return new ResponseEntity<Collection<AppointmentDTO>>(appointmentDTOs,HttpStatus.OK);
	}
	
	@PutMapping("{id}/patient/{patientId}/schedule")
	public ResponseEntity<?> schedulePredefinedAppointment(@PathVariable int id, @PathVariable int patientId){
		try {
			appointmentService.schedulePredefinedAppointment(id, patientId);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (BadRequestException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while scheduling an appointment.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/search/{startTime}")
	public ResponseEntity<Collection<AppointmentDTO>> searchByStartTime(@PathVariable String startTime ,@RequestBody ArrayList<AppointmentDTO> appointmentDTOs){
		Collection<AppointmentDTO> searchResult = appointmentService.searchByStartTime(startTime, appointmentDTOs);
		return new ResponseEntity<Collection<AppointmentDTO>>(searchResult, HttpStatus.OK);
	}
	
	@GetMapping("/doctor/{id_doctor}/pharmacy/{id_pharmacy}/scheduled")
	public ResponseEntity<Collection<AppointmentEventDTO>> getDoctorScheduledAppointmentsInPharamacy(@PathVariable int id_doctor, @PathVariable int id_pharmacy){
		try {
			Collection<AppointmentEventDTO> appointmentEventDTOs = AppointmentEventDTOMapper.toAppointmentEventDTOs(appointmentService.getDoctorScheduledAppointmentsInPharamacy(id_doctor, id_pharmacy));
			return new ResponseEntity<Collection<AppointmentEventDTO>>(appointmentEventDTOs,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/pharmacy/{pharmacyId}/created")
	public ResponseEntity<Collection<AddAppointmentDTO>> findAllCreatedByPharmacyDermatologist(@PathVariable int pharmacyId){
		try {
		Collection<AddAppointmentDTO> appointmentDTOs = 
				AppointmentMapper.toAddAppointmentDTOs(appointmentService.findAllCreatedByPharmacyDermatologist(pharmacyId));
		
		return new ResponseEntity<Collection<AddAppointmentDTO>>(appointmentDTOs,HttpStatus.OK);
		
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping("/doctor/{id_doctor}/date-time/{date}/{start_time}/{end_time}")
	public ResponseEntity<Boolean> isAppointmentAvailableToCreate(@PathVariable int id_doctor, @PathVariable String date, @PathVariable String start_time, @PathVariable String end_time){
		boolean result = appointmentService.isAppointmentAvailableToCreate(id_doctor, date, start_time, end_time);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody AddAppointmentDTO appointmentDTO) {
		if(vacationRequestService.isDoctorOnVacation(appointmentDTO.idDoctor, appointmentDTO.idPharmacy, appointmentDTO.startTime)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(!workingTimeService.isDoctorWorkInPharmacy(appointmentDTO.idPharmacy, appointmentDTO.idDoctor, appointmentDTO.startTime, appointmentDTO.endTime)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(!appointmentService.isAppointmentAvailableToCreate(appointmentDTO.idDoctor, appointmentDTO.startTime, appointmentDTO.startTime, appointmentDTO.endTime)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try{
			appointmentService.add(appointmentDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	

}

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
import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentEventDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentForExaminationDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.mapper.AppointmentEventDTOMapper;
import rs.ac.uns.ftn.isaproject.mapper.AppointmentMapper;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;

@RestController
@RequestMapping(value = "api/appointment")
public class AppointmentController {

	private AppointmentService appointmentService;
	
	@Autowired
	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	
	@PutMapping("/{id}/unperformed")
	public ResponseEntity<Void> changeStatusToUnperformed(@PathVariable int id){
		try {
			appointmentService.changeStatus(id, AppointmentStatus.Unperformed);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<Collection<AppointmentEventDTO>> getDoctorAppointments(@PathVariable int id){
		Collection<AppointmentEventDTO> appointmentEventDTOs = AppointmentEventDTOMapper.toAppointmentEventDTOs(appointmentService.getDoctorAppointments(id));
		return new ResponseEntity<Collection<AppointmentEventDTO>>(appointmentEventDTOs,HttpStatus.OK);
	}
	
	@GetMapping("pharmacy/{pharmacyId}/doctor/{doctorId}")
	public ResponseEntity<Collection<AppointmentDTO>> findAllCreatedByPharmacyAndDoctor(@PathVariable int pharmacyId, @PathVariable int doctorId){
		Collection<AppointmentDTO> appointmentDTOs = AppointmentMapper.toAppointmentDTOs(appointmentService.findAllCreatedByPharmacyAndDoctor(pharmacyId, doctorId));
		return new ResponseEntity<Collection<AppointmentDTO>>(appointmentDTOs,HttpStatus.OK);
	}
	
	@PutMapping("{id}/patient/{patientId}/schedule")
	public ResponseEntity<?> scheduleAppointment(@PathVariable int id, @PathVariable int patientId){
		try {
			appointmentService.scheduleAppointment(id, patientId);
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
	
}

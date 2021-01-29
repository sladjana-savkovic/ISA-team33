package rs.ac.uns.ftn.isaproject.controller.examinations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

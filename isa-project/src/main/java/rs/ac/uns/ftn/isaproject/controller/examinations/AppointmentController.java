package rs.ac.uns.ftn.isaproject.controller.examinations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;

@RestController
@RequestMapping(value = "api/appointment")
public class AppointmentController {

	private AppointmentService appointmentService;
	
	@Autowired
	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
}

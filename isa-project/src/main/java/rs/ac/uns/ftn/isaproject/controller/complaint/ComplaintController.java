package rs.ac.uns.ftn.isaproject.controller.complaint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.service.complaint.ComplaintService;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;
import rs.ac.uns.ftn.isaproject.service.users.PatientService;
import rs.ac.uns.ftn.isaproject.service.users.VacationRequestService;
import rs.ac.uns.ftn.isaproject.service.users.WorkingTimeService;

@RestController
@RequestMapping(value = "api/complaint")
public class ComplaintController {

	private ComplaintService complaintService;
	
	@Autowired
	public ComplaintController(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	
}

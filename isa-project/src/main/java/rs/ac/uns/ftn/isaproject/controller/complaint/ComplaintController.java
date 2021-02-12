package rs.ac.uns.ftn.isaproject.controller.complaint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AddNotificationDTO;
import rs.ac.uns.ftn.isaproject.dto.ComplaintDTO;
import rs.ac.uns.ftn.isaproject.mapper.ComplaintMapper;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.complaint.ComplaintService;
import rs.ac.uns.ftn.isaproject.service.notification.EmailService;

@RestController
@RequestMapping(value = "api/complaint")
public class ComplaintController {

	private ComplaintService complaintService;
	private EmailService emailService;
	
	@Autowired
	public ComplaintController(ComplaintService complaintService, EmailService emailService) {
		this.complaintService = complaintService;
		this.emailService = emailService;
	}
	
	
	@PostMapping(path = "/add", consumes = "application/json")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> add(@RequestBody ComplaintDTO complaintDTO){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			complaintDTO.patientId = u.getUser().getId();
			if (complaintDTO.subjectType.equals("Dermatologist") || complaintDTO.subjectType.equals("Pharmacist")) {
				complaintService.addComplaintToDoctor(complaintDTO);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			}
			else if (complaintDTO.subjectType.equals("Pharmacy")) {
				complaintService.addComplaintToPharmacy(complaintDTO);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			}
			return new ResponseEntity<>("Invalid parameters!", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('SYSTEMADMIN')")
	public ResponseEntity<Collection<ComplaintDTO>> getAllComplaints() {
		try {
			Collection<ComplaintDTO> complaintDTOs = ComplaintMapper.toComplaintDTOs(complaintService.getUnansweredComplaintsDoctor(), complaintService.getUnansweredComplaintsPharmacy());
			if (complaintDTOs == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Collection<ComplaintDTO>>(complaintDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping(path = "/{complaintId}/{subjectType}/reply", consumes = "application/json")
	@PreAuthorize("hasRole('SYSTEMADMIN')")
	public ResponseEntity<?> reply(@PathVariable int complaintId, @PathVariable String subjectType, @RequestBody AddNotificationDTO notificationDTO){
		try {
			if (subjectType.equals("Doctor")) {
				complaintService.replyToComplaintDoctor(complaintId);
				emailService.sendEmail(notificationDTO);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else if (subjectType.equals("Pharmacy")) {
				complaintService.replyToComplaintPharmacy(complaintId);
				emailService.sendEmail(notificationDTO);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			else 
				return new ResponseEntity<>("Invalid parameters: " + subjectType, HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}

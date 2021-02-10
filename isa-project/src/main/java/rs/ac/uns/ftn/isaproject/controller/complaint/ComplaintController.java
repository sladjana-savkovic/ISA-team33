package rs.ac.uns.ftn.isaproject.controller.complaint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.EntityNotFoundException;

import rs.ac.uns.ftn.isaproject.dto.ComplaintDTO;
import rs.ac.uns.ftn.isaproject.service.complaint.ComplaintService;

@RestController
@RequestMapping(value = "api/complaint")
public class ComplaintController {

	private ComplaintService complaintService;
	
	@Autowired
	public ComplaintController(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	
	@PostMapping(path = "/add/{subject}", consumes = "application/json")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> add(@PathVariable String subject, @RequestBody ComplaintDTO complaintDTO){
		try {
			if (subject.equals("doctor")) {
				complaintService.addComplaintToDoctor(complaintDTO);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			}
			else if (subject.equals("pharmacy")) {
				complaintService.addComplaintToPharmacy(complaintDTO);
				return new ResponseEntity<Void>(HttpStatus.CREATED);
			}
			return new ResponseEntity<>("Invalid path parameters", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}

package rs.ac.uns.ftn.isaproject.controller.complaint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import rs.ac.uns.ftn.isaproject.dto.ComplaintDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.mapper.ComplaintMapper;
import rs.ac.uns.ftn.isaproject.mapper.DrugMapper;
import rs.ac.uns.ftn.isaproject.service.complaint.ComplaintService;

@RestController
@RequestMapping(value = "api/complaint")
public class ComplaintController {

	private ComplaintService complaintService;
	
	@Autowired
	public ComplaintController(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	
	@PostMapping(path = "/add", consumes = "application/json")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> add(@RequestBody ComplaintDTO complaintDTO){
		try {
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
	
	
}

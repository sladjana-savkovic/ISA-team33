package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.PharmacyActionDTO;
import rs.ac.uns.ftn.isaproject.service.pharmacy.PharmacyActionService;

@RestController
@RequestMapping(value = "api/action")
public class PharmacyActionController {
	
	private PharmacyActionService pharmacyActionService;
	
	@Autowired
	public PharmacyActionController(PharmacyActionService pharmacyActionService) {
		this.pharmacyActionService = pharmacyActionService;
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> add(@RequestBody PharmacyActionDTO pharmacyActionDTO) {
		try {
			pharmacyActionService.save(pharmacyActionDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e) {
			return new ResponseEntity<>("An error occurred while creating new pharmacy action.", HttpStatus.NOT_FOUND);
		}
	}
}

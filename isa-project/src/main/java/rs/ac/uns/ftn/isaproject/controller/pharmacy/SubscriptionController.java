package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.Collection;

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

import rs.ac.uns.ftn.isaproject.dto.AddSubscriptionDTO;
import rs.ac.uns.ftn.isaproject.dto.SubscriptionDTO;
import rs.ac.uns.ftn.isaproject.mapper.SubscriptionMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Subscription;
import rs.ac.uns.ftn.isaproject.service.pharmacy.SubscriptionService;

@RestController
@RequestMapping(value = "api/subscription")
public class SubscriptionController {
	
	private SubscriptionService subscriptionService;
	
	@Autowired
	public SubscriptionController(SubscriptionService subscriptionService) {
		this.subscriptionService = subscriptionService;
	}

	@GetMapping("/{id}/pharmacy-patients")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Collection<Integer>> getEmailsOfSubscriptionPatients(@PathVariable int id){
		try {
			return new ResponseEntity<Collection<Integer>>(subscriptionService.getSubscribedPatientsByPharmacy(id), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping(value = "/add", consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<String> add(@RequestBody AddSubscriptionDTO dto){
		try {
			subscriptionService.add(dto);
			return new ResponseEntity<String>("Successful subscription!", HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/{id}/patient")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<Collection<SubscriptionDTO>> findSubscriptionsByPatientId(@PathVariable int id){
		try {
			Collection<SubscriptionDTO> subscriptionsDTOs = SubscriptionMapper.toSubscriptionDTOs(subscriptionService.findSubscriptionsByPatientId(id));
			return new ResponseEntity<Collection<SubscriptionDTO>>(subscriptionsDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}

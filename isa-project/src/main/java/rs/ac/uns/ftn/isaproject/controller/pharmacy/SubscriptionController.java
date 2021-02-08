package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> getEmailsOfSubscriptionPatients(@PathVariable int id){
		try {
			return new ResponseEntity<Collection<Integer>>(subscriptionService.getSubscribedPatientsByPharmacy(id), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting subscribed patients.", HttpStatus.BAD_REQUEST);
		}
	}
}

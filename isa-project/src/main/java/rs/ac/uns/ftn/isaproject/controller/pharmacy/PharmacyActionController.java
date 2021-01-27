package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.service.pharmacy.PharmacyActionService;

@RestController
@RequestMapping(value = "api/action")
public class PharmacyActionController {
	
	private PharmacyActionService pharmacyActionService;
	
	@Autowired
	public PharmacyActionController(PharmacyActionService pharmacyActionService) {
		this.pharmacyActionService = pharmacyActionService;
	}
}

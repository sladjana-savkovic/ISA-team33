package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.service.pharmacy.PharmacyService;

@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyController {

	private PharmacyService pharmacyService;
	
	@Autowired
	public PharmacyController(PharmacyService pharmacyService) {
		this.pharmacyService = pharmacyService;
	}
}

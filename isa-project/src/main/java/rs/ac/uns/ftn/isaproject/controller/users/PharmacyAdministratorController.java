package rs.ac.uns.ftn.isaproject.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.service.users.PharmacyAdministratorService;

@RestController
@RequestMapping(value = "api/pharmacy-admin")
public class PharmacyAdministratorController {

	private PharmacyAdministratorService administratorService;
	
	@Autowired
	public PharmacyAdministratorController(PharmacyAdministratorService administratorService) {
		this.administratorService = administratorService;
	}
}

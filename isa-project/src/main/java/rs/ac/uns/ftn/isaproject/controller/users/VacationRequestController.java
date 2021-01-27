package rs.ac.uns.ftn.isaproject.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.service.users.VacationRequestService;

@RestController
@RequestMapping(value = "api/vacation")
public class VacationRequestController {

	private VacationRequestService vacationService;
	
	@Autowired
	public VacationRequestController(VacationRequestService vacationService) {
		this.vacationService = vacationService;
	}
}

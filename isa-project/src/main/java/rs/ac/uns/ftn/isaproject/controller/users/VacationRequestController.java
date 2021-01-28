package rs.ac.uns.ftn.isaproject.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddVacationRequestDTO;
import rs.ac.uns.ftn.isaproject.service.users.VacationRequestService;

@RestController
@RequestMapping(value = "api/vacation")
public class VacationRequestController {

	private VacationRequestService vacationService;
	
	@Autowired
	public VacationRequestController(VacationRequestService vacationService) {
		this.vacationService = vacationService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> Add(@RequestBody AddVacationRequestDTO vacationRequestDTO){
		try {
			vacationService.Add(vacationRequestDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
}

package rs.ac.uns.ftn.isaproject.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.WorkingTimeDTO;
import rs.ac.uns.ftn.isaproject.service.users.WorkingTimeService;

@RestController
@RequestMapping(value = "api/working-time")
public class WorkingTimeController {

	private WorkingTimeService workingTimeService;
	
	@Autowired
	public WorkingTimeController(WorkingTimeService workingTimeService) {
		this.workingTimeService = workingTimeService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody WorkingTimeDTO workingTimeDTO){
		try {
			workingTimeService.add(workingTimeDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
}

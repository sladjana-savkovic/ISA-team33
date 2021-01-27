package rs.ac.uns.ftn.isaproject.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.service.users.WorkingTimeService;

@RestController
@RequestMapping(value = "api/working-time")
public class WorkingTimeController {

	private WorkingTimeService workingTimeService;
	
	@Autowired
	public WorkingTimeController(WorkingTimeService workingTimeService) {
		this.workingTimeService = workingTimeService;
	}
}

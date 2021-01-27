package rs.ac.uns.ftn.isaproject.controller.examinations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.service.examinations.TherapyService;

@RestController
@RequestMapping(value = "api/therapy")
public class TherapyController {

	private TherapyService therapyService;
	
	@Autowired
	public TherapyController(TherapyService therapyService) {
		this.therapyService = therapyService;
	}
}

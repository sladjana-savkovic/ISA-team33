package rs.ac.uns.ftn.isaproject.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.service.users.DoctorService;

@RestController
@RequestMapping(value = "api/doctor")
public class DoctorController {

	private DoctorService doctorService;
	
	@Autowired
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
}

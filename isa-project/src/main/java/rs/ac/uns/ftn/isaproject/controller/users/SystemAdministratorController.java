package rs.ac.uns.ftn.isaproject.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.AddSystemAdministratorDTO;
import rs.ac.uns.ftn.isaproject.service.users.SystemAdministratorService;

@RestController
@RequestMapping(value = "api/system-admin")
public class SystemAdministratorController {

	private SystemAdministratorService systemAdministratorService;
	
	@Autowired
	public SystemAdministratorController(SystemAdministratorService systemAdministratorService) {
		this.systemAdministratorService = systemAdministratorService;
	}
		

	@RequestMapping(path = "/add", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
	public ResponseEntity<Void> add(@RequestBody AddSystemAdministratorDTO systemAdministratorDTO){
		try {
			systemAdministratorService.add(systemAdministratorDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}

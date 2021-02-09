package rs.ac.uns.ftn.isaproject.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.UserAccountDTO;
import rs.ac.uns.ftn.isaproject.mapper.UserAccountMapper;
import rs.ac.uns.ftn.isaproject.service.users.UserAccountService;
import org.springframework.http.MediaType;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAccountController {
	
	private UserAccountService userAccountService;
	
	@Autowired
	public UserAccountController(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> findUserAccount(@PathVariable int userId) {
		try {
			UserAccountDTO userAccountDTO = UserAccountMapper.toAccountDTO(userAccountService.findByUserId(userId));
			return new ResponseEntity<UserAccountDTO>(userAccountDTO, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("The requested user account doesn't exist in the database.", HttpStatus.NOT_FOUND);
		}
	}
	
}

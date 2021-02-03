package rs.ac.uns.ftn.isaproject.controller.users;

import java.nio.file.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
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

	
	@GetMapping("/{id}")
	public UserAccount getOne(@PathVariable int id) throws AccessDeniedException {
		return userAccountService.findById(id);
	}
	
	@PutMapping("/{id}/password/{oldPassword}/{newPassword}")
	public ResponseEntity<?> updatePassword(@PathVariable int id,@PathVariable String oldPassword, @PathVariable String newPassword){
		try {
			userAccountService.updatePassword(id,oldPassword, newPassword);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (BadRequestException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while updating user's password.", HttpStatus.BAD_REQUEST);
		}
	}
}

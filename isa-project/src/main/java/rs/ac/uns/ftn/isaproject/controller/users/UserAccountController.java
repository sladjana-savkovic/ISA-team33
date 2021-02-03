package rs.ac.uns.ftn.isaproject.controller.users;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.users.UserAccountService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAccountController {


	@Autowired
	private UserAccountService userAccountService;


	@GetMapping("/user/{userId}")
	public UserAccount loadById(@PathVariable Long userId) {
		return this.userAccountService.findById(userId);
	}


	@GetMapping("/whoami")
	public UserAccount user(Principal user) {
		return this.userAccountService.findByUsername(user.getName());
	}
	
		
}

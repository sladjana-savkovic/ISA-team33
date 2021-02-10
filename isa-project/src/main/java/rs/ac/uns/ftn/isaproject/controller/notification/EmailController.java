package rs.ac.uns.ftn.isaproject.controller.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddNotificationDTO;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.notification.EmailService;
import rs.ac.uns.ftn.isaproject.service.users.UserAccountService;

@RestController
@RequestMapping(value = "api/email")
public class EmailController {

	private EmailService emailService;
	private UserAccountService userAccountService;
	
	@Autowired
	public EmailController(EmailService emailService,UserAccountService userAccountService) {
		this.emailService = emailService;
		this.userAccountService = userAccountService;
	}
	
	@PostMapping()
	public  ResponseEntity<?> sendEmail(@RequestBody AddNotificationDTO notificationDTO) {
		try {
			emailService.sendEmail(notificationDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/{userId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST', 'ROLE_PHARMACYADMIN', 'ROLE_SYSTEMADMIN', ROLE_PATIENT)")
	public  ResponseEntity<?> sendEmailToUser(@PathVariable int userId, @RequestBody AddNotificationDTO notificationDTO) {
		try {
			UserAccount userAccount = userAccountService.findByUserId(userId);
			notificationDTO.email = userAccount.getUsername();
			notificationDTO.name = userAccount.getUser().getName();
			emailService.sendEmail(notificationDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}

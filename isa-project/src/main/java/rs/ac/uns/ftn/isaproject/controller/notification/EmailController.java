package rs.ac.uns.ftn.isaproject.controller.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddNotificationDTO;
import rs.ac.uns.ftn.isaproject.service.notification.EmailService;

@RestController
@RequestMapping(value = "api/email")
public class EmailController {

	private EmailService emailService;
	
	@Autowired
	public EmailController(EmailService emailService) {
		this.emailService = emailService;
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
	
}

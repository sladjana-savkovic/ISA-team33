package rs.ac.uns.ftn.isaproject.service.notification;

import org.springframework.mail.MailException;
import rs.ac.uns.ftn.isaproject.dto.AddNotificationDTO;
import rs.ac.uns.ftn.isaproject.security.auth.ConfirmationToken;

public interface EmailService {

	void sendEmail(AddNotificationDTO notificationDTO) throws MailException, InterruptedException;
	
	void sendActivationEmail(String email, ConfirmationToken confirmationToken) throws MailException, InterruptedException;
}

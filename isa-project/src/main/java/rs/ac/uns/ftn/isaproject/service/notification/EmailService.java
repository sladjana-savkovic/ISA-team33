package rs.ac.uns.ftn.isaproject.service.notification;

import org.springframework.mail.MailException;
import rs.ac.uns.ftn.isaproject.dto.AddNotificationDTO;

public interface EmailService {

	void sendEmail(AddNotificationDTO notificationDTO) throws MailException, InterruptedException;
	
}

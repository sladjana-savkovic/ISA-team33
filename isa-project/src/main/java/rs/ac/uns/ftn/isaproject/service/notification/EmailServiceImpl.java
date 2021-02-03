package rs.ac.uns.ftn.isaproject.service.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.AddNotificationDTO;
import org.springframework.core.env.Environment;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender mailSender;
	private Environment environment;
	
	@Autowired public EmailServiceImpl(JavaMailSender mailSender, Environment environment) {
		this.mailSender = mailSender;
		this.environment = environment;
	}
	
	@Async
	public void sendEmail(AddNotificationDTO notificationDTO) throws MailException, InterruptedException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(notificationDTO.email);
		mail.setFrom(environment.getProperty("spring.mail.username"));
		mail.setSubject(notificationDTO.subject);
		mail.setText(notificationDTO.message);
		mailSender.send(mail);
	}
}

package rs.ac.uns.ftn.isaproject.controller.notification;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.NotificationDTO;
import rs.ac.uns.ftn.isaproject.mapper.NotificationMapper;
import rs.ac.uns.ftn.isaproject.service.notification.NotificationService;

@RestController
@RequestMapping(value = "api/notification")
public class NotificationController {
	
	private NotificationService notificationService;
	
	@Autowired
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	@GetMapping("/{id}/pharmacy")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Collection<NotificationDTO>> findByPharmacyId(@PathVariable int id){
		Collection<NotificationDTO> notificationDTOs = NotificationMapper.toNotificationDTOs(notificationService.findByPharmacyId(id));
		return new ResponseEntity<Collection<NotificationDTO>>(notificationDTOs, HttpStatus.OK);
	}
}

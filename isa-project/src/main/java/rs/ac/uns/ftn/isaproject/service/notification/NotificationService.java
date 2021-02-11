package rs.ac.uns.ftn.isaproject.service.notification;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.NotificationDTO;
import rs.ac.uns.ftn.isaproject.model.notification.Notification;

public interface NotificationService {

	void send(NotificationDTO notificationDTO);
	Collection<Notification> findByPharmacyId(int id);
	void sendAll(Collection<NotificationDTO> notificationDTOs);
}

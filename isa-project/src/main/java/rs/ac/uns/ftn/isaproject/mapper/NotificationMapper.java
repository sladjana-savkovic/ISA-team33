package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.NotificationDTO;
import rs.ac.uns.ftn.isaproject.model.notification.Notification;

public class NotificationMapper {
	
	public static Collection<NotificationDTO> toNotificationDTOs(Collection<Notification> notifications){
		Collection<NotificationDTO> notificationDTOs = new ArrayList<>();
		for(Notification n:notifications) {
			notificationDTOs.add(new NotificationDTO(n.getId(), n.getDrug().getId(), n.getPharmacy().getId(), n.getDrug().getName(), n.getDrug().getTypeOfDrug().toString(), n.getDrug().getTypeOfDrugsForm().toString(), n.getCreationDate()));
		}
		return notificationDTOs;
	}

}

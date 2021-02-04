package rs.ac.uns.ftn.isaproject.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.isaproject.model.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{

}

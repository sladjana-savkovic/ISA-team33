package rs.ac.uns.ftn.isaproject.repository.notification;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{

	@Query("select n from Notification n where n.pharmacy.id = ?1")
	Collection<Notification> findByPharmacyId(int id);
}

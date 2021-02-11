package rs.ac.uns.ftn.isaproject.service.notification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.NotificationDTO;
import rs.ac.uns.ftn.isaproject.model.notification.Notification;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.repository.notification.NotificationRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	private NotificationRepository notificationRepository;
	private DrugRepository drugRepository;
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	public NotificationServiceImpl(NotificationRepository notificationRepository, DrugRepository drugRepository,PharmacyRepository pharmacyRepository) {
		this.notificationRepository = notificationRepository;
		this.drugRepository = drugRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public void send(NotificationDTO notificationDTO) {
		Notification notification = new Notification();
		Drug drug = drugRepository.getOne(notificationDTO.drugId);
		Pharmacy pharmacy = pharmacyRepository.getOne(notificationDTO.pharmacyId);
		
		notification.setDrug(drug);
		notification.setPharmacy(pharmacy);
		notification.setCreationDate(LocalDateTime.now());
		
		notificationRepository.save(notification);
	}

	@Override
	public Collection<Notification> findByPharmacyId(int id) {
		return notificationRepository.findByPharmacyId(id);
	}

	@Override
	public void sendAll(Collection<NotificationDTO> notificationDTOs) {
		Collection<Notification> notifications = new ArrayList<>();
		Notification notification = new Notification();
		Drug drug;
		Pharmacy pharmacy;
		
		for(NotificationDTO notificationDTO:notificationDTOs) {
			drug = drugRepository.getOne(notificationDTO.drugId);
			pharmacy = pharmacyRepository.getOne(notificationDTO.pharmacyId);
			
			notification.setDrug(drug);
			notification.setPharmacy(pharmacy);
			notification.setCreationDate(LocalDateTime.now());
			
			notifications.add(notification);
		}
		
		notificationRepository.saveAll(notifications);	
	}

}

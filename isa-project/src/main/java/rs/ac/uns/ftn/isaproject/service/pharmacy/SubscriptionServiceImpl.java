package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddSubscriptionDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Subscription;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.SubscriptionRepository;
import rs.ac.uns.ftn.isaproject.repository.users.PatientRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
	
	private SubscriptionRepository subscriptionRepository;
	private PatientRepository patientRepository;
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, PatientRepository patientRepository, PharmacyRepository pharmacyRepository) {
		this.subscriptionRepository = subscriptionRepository;
		this.patientRepository = patientRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public Collection<Subscription> findByPharmacyId(int id) {
		return subscriptionRepository.findByPharmacyId(id);
	}

	@Override
	public Collection<Integer> getSubscribedPatientsByPharmacy(int pharmacyId) {
		Collection<Integer> idies = new ArrayList<Integer>();
		Collection<Subscription> subscriptions = findByPharmacyId(pharmacyId);
		for(Subscription s : subscriptions) {
			idies.add(s.getPatient().getId());
		}
		return idies;
	}

	@Override
	public void add(AddSubscriptionDTO dto) throws Exception {
		Subscription subscription = subscriptionRepository.findByPharmacyIdAndPatientId(dto.pharmacyId, dto.patientId);
		if (subscription == null) {
			subscription = new Subscription();
			subscription.setPatient(patientRepository.getOne(dto.patientId));
			subscription.setPharmacy(pharmacyRepository.getOne(dto.pharmacyId));
			subscription.setCanceled(false);
			subscriptionRepository.save(subscription);
			return;
		}
		throw new Exception("There is already a subscription.");	  
	}

	@Override
	public Collection<Subscription> findSubscriptionsByPatientId(int id) {
		Collection<Subscription> subscriptions = subscriptionRepository.findSubscriptionsByPatientId(id);			
		return subscriptions;
	}

	@Override
	public void cancelSubscription(int id) {
		Subscription subscription = subscriptionRepository.getOne(id);
		subscription.setCanceled(true);
		subscriptionRepository.save(subscription);
	}
	
}

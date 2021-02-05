package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Subscription;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
	
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;	
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

}

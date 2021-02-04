package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Subscription;

public interface SubscriptionService {

	Collection<Subscription> findByPharmacyId(int id);
	Collection<Integer> getSubscribedPatientsByPharmacy(int pharmacyId);
}

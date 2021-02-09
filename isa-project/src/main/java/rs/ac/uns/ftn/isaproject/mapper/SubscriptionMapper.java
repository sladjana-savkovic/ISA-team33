package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.SubscriptionDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Subscription;

public class SubscriptionMapper {

	public static Collection<SubscriptionDTO> toSubscriptionDTOs(Collection<Subscription> subscriptions){
		Collection<SubscriptionDTO> subscriptionDTOs = new ArrayList<>();
		for(Subscription s : subscriptions) {			
			SubscriptionDTO dto = new SubscriptionDTO();
			dto.subscriptionId = s.getId();
			dto.pharmacyName = s.getPharmacy().getName();
			subscriptionDTOs.add(dto);
		}
		return subscriptionDTOs;
	}
}

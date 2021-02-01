package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;

public interface DrugOfferService {
	
	void acceptOffer(int id);
	void rejectOffer(int id);
	Collection<DrugOffer> findByPharmacyOrderId(int id);
	Collection<DrugOffer> findByPharmacyId(int id);
	DrugOffer findById(int id);
}

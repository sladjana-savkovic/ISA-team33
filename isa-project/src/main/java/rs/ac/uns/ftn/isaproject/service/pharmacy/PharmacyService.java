package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;

public interface PharmacyService {

	Pharmacy findOneById(int id);
	Collection<Pharmacy> findAll();
}

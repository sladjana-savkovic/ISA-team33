package rs.ac.uns.ftn.isaproject.service.pharmacy;

import rs.ac.uns.ftn.isaproject.dto.PharmacyDTO;

import java.time.LocalDateTime;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;

public interface PharmacyService {

	Pharmacy findOneById(int id);
	
	void add(PharmacyDTO pharmacyDTO);

	Collection<Pharmacy> findAll();

	Collection<Pharmacy> findAvailablePharmacy(LocalDateTime date);

}

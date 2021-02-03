package rs.ac.uns.ftn.isaproject.service.pharmacy;

import rs.ac.uns.ftn.isaproject.dto.PharmacyDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;

public interface PharmacyService {

	Pharmacy findOneById(int id);
	
	void add(PharmacyDTO pharmacyDTO);
}

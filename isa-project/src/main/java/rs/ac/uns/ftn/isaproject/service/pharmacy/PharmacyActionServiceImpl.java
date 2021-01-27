package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyActionRepository;

@Service
public class PharmacyActionServiceImpl implements PharmacyActionService{

	private PharmacyActionRepository pharmacyActionRepository;
	
	@Autowired
	public PharmacyActionServiceImpl(PharmacyActionRepository pharmacyActionRepository) {
		this.pharmacyActionRepository = pharmacyActionRepository;
	}
}

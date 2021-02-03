package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;

@Service
public class PharmacyServiceImpl implements PharmacyService {

	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	public PharmacyServiceImpl(PharmacyRepository pharmacyRepository) {
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public Pharmacy findOneById(int id) {
		
		return pharmacyRepository.findOneById(id);
	}

	@Override
	public Collection<Pharmacy> findAll() {
		return pharmacyRepository.findAll();
	}
}

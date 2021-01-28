package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyOrderRepository;

@Service
public class PharmacyOrderServiceImpl implements PharmacyOrderService{

	private PharmacyOrderRepository pharmacyOrderRepository;
	
	@Autowired
	public PharmacyOrderServiceImpl(PharmacyOrderRepository pharmacyOrderRepository) {
		this.pharmacyOrderRepository = pharmacyOrderRepository;
	}
}
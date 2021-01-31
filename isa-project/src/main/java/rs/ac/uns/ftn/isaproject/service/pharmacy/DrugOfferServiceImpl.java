package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.enums.OfferStatus;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugOfferRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityOrderRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyOrderRepository;

@Service
public class DrugOfferServiceImpl implements DrugOfferService{
	
	private DrugOfferRepository drugOfferRepository;
	private PharmacyOrderRepository pharmacyOrderRepository;
	
	@Autowired
	public DrugOfferServiceImpl(DrugOfferRepository drugOfferRepository, PharmacyOrderRepository pharmacyOrderRepository) {
		this.drugOfferRepository = drugOfferRepository;
		this.pharmacyOrderRepository = pharmacyOrderRepository;
	}

	@Override
	public void acceptOffer(int id) {
		DrugOffer drugOffer = drugOfferRepository.getOne(id);
		PharmacyOrder pharmacyOrder = pharmacyOrderRepository.getOne(drugOffer.getPharmacyOrder().getId());
		if(pharmacyOrder.getLimitDate().isBefore(LocalDate.now())) {
			drugOffer.setStatus(OfferStatus.Accepted);
			drugOfferRepository.save(drugOffer);
		}
		
	}

	@Override
	public Collection<DrugOffer> findByPharmacyOrderId(int id) {
		return drugOfferRepository.findByPharmacyOrderId(id);
	}


}

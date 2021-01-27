package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugOfferRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityRepository;

@Service
public class DrugOfferServiceImpl implements DrugOfferService{
	
	private DrugOfferRepository drugOfferRepository;
	private DrugQuantityRepository drugQuantityRepository;
	
	@Autowired
	public DrugOfferServiceImpl(DrugOfferRepository drugOfferRepository, DrugQuantityRepository drugQuantityRepository) {
		this.drugOfferRepository = drugOfferRepository;
		this.drugQuantityRepository = drugQuantityRepository;
	}

}

package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;

import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityPharmacyRepository;

public class DrugQuantityPharmacyServiceImpl implements DrugQuantityPharmacyService{

	private DrugQuantityPharmacyRepository quantityPharmacyRepository;
	
	@Autowired
	public DrugQuantityPharmacyServiceImpl(DrugQuantityPharmacyRepository quantityPharmacyRepository) {
		this.quantityPharmacyRepository = quantityPharmacyRepository;
	}
}

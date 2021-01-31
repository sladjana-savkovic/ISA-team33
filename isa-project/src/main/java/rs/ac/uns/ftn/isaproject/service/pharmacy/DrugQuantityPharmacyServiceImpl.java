package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityPharmacyRepository;

@Service
public class DrugQuantityPharmacyServiceImpl implements DrugQuantityPharmacyService{

	private DrugQuantityPharmacyRepository quantityPharmacyRepository;
	
	@Autowired
	public DrugQuantityPharmacyServiceImpl(DrugQuantityPharmacyRepository quantityPharmacyRepository) {
		this.quantityPharmacyRepository = quantityPharmacyRepository;
	}

	@Override
	public boolean checkDrugAvailability(int drugId, int pharmacyId) {
		Collection<DrugQuantityPharmacy> quantityPharmacies = quantityPharmacyRepository.findAll();
		
		for(DrugQuantityPharmacy quantity:quantityPharmacies) {
			if(quantity.getDrug().getId() == drugId && quantity.getPharmacy().getId() == pharmacyId && quantity.getQuantity() > 0)
				return true;
		}
		return false;
	}

	@Override
	public boolean reduceDrugQuantity(int drugId, int pharmacyId, int quantity) {
		Collection<DrugQuantityPharmacy> quantityPharmacies = quantityPharmacyRepository.findAll();
		
		for(DrugQuantityPharmacy drugQuantity:quantityPharmacies) {
			if(drugQuantity.getDrug().getId() == drugId && drugQuantity.getPharmacy().getId() == pharmacyId) {
				if(drugQuantity.getQuantity() - quantity > 0) {
					drugQuantity.setQuantity(drugQuantity.getQuantity() - quantity);
					quantityPharmacyRepository.save(drugQuantity);
					return true;
				}
			}
		}
		return false;
	}
}

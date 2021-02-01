package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityPharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;

@Service
public class DrugQuantityPharmacyServiceImpl implements DrugQuantityPharmacyService{

	private DrugQuantityPharmacyRepository quantityPharmacyRepository;
	private DrugRepository drugRepository;
	
	@Autowired
	public DrugQuantityPharmacyServiceImpl(DrugQuantityPharmacyRepository quantityPharmacyRepository, DrugRepository drugRepository) {
		this.quantityPharmacyRepository = quantityPharmacyRepository;
		this.drugRepository = drugRepository;
	}

	@Override
	public Collection<Drug> findDrugsByPharmacyId(int id) {
		Collection<DrugQuantityPharmacy> drugQuantities = quantityPharmacyRepository.findByPharmacyId(id);
		Collection<Drug> drugs = new ArrayList<Drug>();
		
		for(DrugQuantityPharmacy d: drugQuantities) {
			drugs.add(drugRepository.getOne(d.getDrug().getId()));
		}
		return drugs;
	}

	@Override
	public boolean checkDrugAvailability(int drugId, int pharmacyId) {
		Collection<DrugQuantityPharmacy> quantityPharmacies = quantityPharmacyRepository.findAll();
		
		for(DrugQuantityPharmacy quantity:quantityPharmacies) {
			if(quantity.getDrug().getId() == drugId && quantity.getPharmacy().getId() == pharmacyId && quantity.getQuantity() > 0)
				return true;
		}
		
		//TODO: Ako lijek nije dostupan salje se notifikacija adminu apoteke
		return false;
	}

	@Override
	public boolean reduceDrugQuantity(int drugId, int pharmacyId) {
		Collection<DrugQuantityPharmacy> quantityPharmacies = quantityPharmacyRepository.findAll();
		
		for(DrugQuantityPharmacy drugQuantity:quantityPharmacies) {
			if(drugQuantity.getDrug().getId() == drugId && drugQuantity.getPharmacy().getId() == pharmacyId) {
				if(drugQuantity.getQuantity() - 1 > 0) {
					drugQuantity.setQuantity(drugQuantity.getQuantity() - 1);
					quantityPharmacyRepository.save(drugQuantity);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Collection<Drug> findAvailableDrugsByPharmacyId(int pharmacyId) {
		Collection<DrugQuantityPharmacy> drugQuantities = quantityPharmacyRepository.findAvailableDrugsByPharmacyId(pharmacyId);
		Collection<Drug> drugs = new ArrayList<Drug>();
		
		for(DrugQuantityPharmacy d: drugQuantities) {
			drugs.add(drugRepository.getOne(d.getDrug().getId()));
		}
		return drugs;
	}

}

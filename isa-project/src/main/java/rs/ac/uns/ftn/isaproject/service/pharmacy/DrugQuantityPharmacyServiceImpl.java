package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

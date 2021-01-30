package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.IngredientRepository;

@Service
public class DrugServiceImpl implements DrugService{

	private DrugRepository drugRepository;
	private IngredientRepository ingredientRepository;
	
	@Autowired
	public DrugServiceImpl(DrugRepository drugRepository, IngredientRepository ingredientRepository) {
		this.drugRepository = drugRepository;
		this.ingredientRepository = ingredientRepository;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public Collection<Drug> findAllByPharmacyId(int id) {
		Collection<Drug> drugs = drugRepository.findAll();
		Collection<Drug> drugsInPharmacy = new ArrayList<>();
		
		for(Drug drug:drugs) {
			Collection<Pharmacy> drugPharmacies = drug.getPharmacies();
			for(Pharmacy pharmacy:drugPharmacies) {
				if(pharmacy.getId() == id) {
					drugsInPharmacy.add(drug);
					break;
				}
			}
		}
		
		return drugsInPharmacy;
	}
}

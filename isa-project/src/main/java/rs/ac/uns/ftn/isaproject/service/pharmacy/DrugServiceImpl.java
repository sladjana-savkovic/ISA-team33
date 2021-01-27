package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

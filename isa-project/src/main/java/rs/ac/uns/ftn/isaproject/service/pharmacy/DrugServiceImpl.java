package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrug;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrugsForm;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
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
	public void add(DrugDTO drugDTO) {
		Drug drug = new Drug();
		
		drug.setName(drugDTO.name);
		drug.setProducer(drugDTO.producer);
		drug.setTypeOfDrug(TypeOfDrug.valueOf(drugDTO.typeOfDrug));
		drug.setTypeOfDrugsForm(TypeOfDrugsForm.valueOf(drugDTO.typeOfDrugsForm));
		
		drugRepository.save(drug);
		
	}
}

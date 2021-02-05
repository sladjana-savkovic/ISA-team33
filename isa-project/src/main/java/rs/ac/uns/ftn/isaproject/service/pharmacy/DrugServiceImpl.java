package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Ingredient;
import rs.ac.uns.ftn.isaproject.dto.AddDrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugSearchDTO;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrug;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrugsForm;
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
	public void add(AddDrugDTO drugDTO) {
		Drug drug = new Drug();		
		drug.setName(drugDTO.name);
		drug.setCode(drugDTO.code);
		drug.setGrade(0);
		drug.setContraindication(drugDTO.contraindication);
		drug.setNotes(drugDTO.notes);
		drug.setProducer(drugDTO.producer);		
		drug.setTypeOfDrug(TypeOfDrug.valueOf(drugDTO.typeOfDrug));		
		drug.setTypeOfDrugsForm(TypeOfDrugsForm.valueOf(drugDTO.drugForm));
		drug.setAllowedOnPrescription(drugDTO.prescription);
		drug.setDailyDose(drugDTO.dailyDose);
		
		Collection<Drug> substituteDrugs = drugRepository.findByIds(drugDTO.substituteDrugList);
		Collection<Ingredient> ingredients = ingredientRepository.findByIds(drugDTO.drugIngredientsList);		
		for (Ingredient i : ingredients) {
			drug.getIngredients().add(i);			
		}
		for (Drug d : substituteDrugs) {
			drug.getSubstituteDrugs().add(d);			
		}				
		drugRepository.save(drug);
	}

	@Override
	public Collection<Drug> getSubstituteDrugs(int id) {
		return drugRepository.getOne(id).getSubstituteDrugs();
	}

	@Override
	public Collection<Drug> getAllDrugs() {
		return drugRepository.findAll();
	}

	@Override
	public Drug getById(int id) {
		return drugRepository.getOne(id);
	}

	@Override
	public Collection<DrugDTO> searchByNameAndGradeAndType(DrugSearchDTO dto) {			
		Collection<DrugDTO> searchResult = new ArrayList<>();			
		for(DrugDTO drug : dto.drugDTOs) {
			if(drug.name.toLowerCase().contains(dto.name.toLowerCase()) & (drug.typeOfDrug.contains(dto.type))) {
				if (dto.grade == -1) {
					searchResult.add(drug);
				}	
				else if (dto.grade == drug.grade) {
					searchResult.add(drug);
				}
			}
		}
		return searchResult;
	}
	
	
}

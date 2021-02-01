package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrug;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDrugsForm;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;

@Service
public class DrugServiceImpl implements DrugService{

	private DrugRepository drugRepository;
	
	@Autowired
	public DrugServiceImpl(DrugRepository drugRepository) {
		this.drugRepository = drugRepository;
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

	@Override
	public Collection<Drug> getSubstituteDrugs(int id) {
		return drugRepository.getOne(id).getSubstituteDrugs();
	}

	@Override
	public Collection<Drug> getAllDrugs() {
		return drugRepository.findAll();
	}
	
}

package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AddDrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugSearchDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

public interface DrugService {
	
	void add(AddDrugDTO drugDTO);
	Collection<Drug> getSubstituteDrugs(int id);
	Collection<Drug> getAllDrugs();
	Drug getById(int id);
	public Collection<DrugDTO> searchByNameAndGradeAndType(DrugSearchDTO dto);
}

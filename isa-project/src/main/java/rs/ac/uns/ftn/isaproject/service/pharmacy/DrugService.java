package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

public interface DrugService {
	
	void add(DrugDTO drugDTO);
	Collection<Drug> getSubstituteDrugs(int id);
	Collection<Drug> getAllDrugs();
}

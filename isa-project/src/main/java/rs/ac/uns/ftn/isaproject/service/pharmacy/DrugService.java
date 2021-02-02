package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public interface DrugService {
	
	void add(DrugDTO drugDTO);
	Collection<Drug> getSubstituteDrugs(int id);
	Collection<Drug> getAllDrugs();
	//Drug getOne(int id);
}

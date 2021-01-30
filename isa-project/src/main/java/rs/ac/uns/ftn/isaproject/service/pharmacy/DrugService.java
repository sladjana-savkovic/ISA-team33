package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;

public interface DrugService {
	
	void add(DrugDTO drugDTO);
	Collection<Drug> findAllByPharmacyId(int id);
}

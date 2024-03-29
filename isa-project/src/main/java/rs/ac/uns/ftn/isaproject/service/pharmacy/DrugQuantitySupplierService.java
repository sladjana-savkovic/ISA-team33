package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AddDrugQuantitySupplierDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantitySupplier;

public interface DrugQuantitySupplierService {
	
	Collection<DrugQuantitySupplier> findBySupplierId(int id);
	void add(AddDrugQuantitySupplierDTO drugQuantityDTO);
	
}

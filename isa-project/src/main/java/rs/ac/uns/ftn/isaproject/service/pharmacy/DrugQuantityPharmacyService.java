package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

public interface DrugQuantityPharmacyService {

	boolean checkDrugAvailability(int drugId, int pharmacyId);
	boolean reduceDrugQuantity(int drugId, int pharmacyId, int quantity);
	Collection<Drug> findDrugsByPharmacyId(int id);
	
}

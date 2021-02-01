package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugQuantityPharmacyDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

public interface DrugQuantityPharmacyService {

	boolean checkDrugAvailability(int drugId, int pharmacyId);
	boolean reduceDrugQuantity(int drugId, int pharmacyId);
	Collection<Drug> findDrugsByPharmacyId(int id);
	Collection<Drug> findAvailableDrugsByPharmacyId(int pharmacyId);
	boolean increaseDrugQuantityPharmacy(int drugId, int pharmacyId, int quantity);
	void addDrugQuantityPharmacy(DrugQuantityPharmacyDTO drugQuantityDTO);
	public Collection<DrugDTO> searchByName(String name, Collection<DrugDTO> drugDTOs);
	void deleteDrugQuantityPharmacy(int idDrug, int idPharmacy);
	
}

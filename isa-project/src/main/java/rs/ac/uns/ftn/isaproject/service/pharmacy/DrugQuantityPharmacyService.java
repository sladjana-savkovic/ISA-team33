package rs.ac.uns.ftn.isaproject.service.pharmacy;

public interface DrugQuantityPharmacyService {

	boolean checkDrugAvailability(int drugId, int pharmacyId);
	boolean reduceDrugQuantity(int drugId, int pharmacyId, int quantity);
}

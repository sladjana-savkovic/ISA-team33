package rs.ac.uns.ftn.isaproject.service.pharmacy;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyOrderDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;

public interface PharmacyOrderService {

	PharmacyOrder findById(int id);
	void addDrugQuantity(DrugQuantityDTO drugQuantityDTO);
	void add(PharmacyOrderDTO pharmacyOrderDTO);
}

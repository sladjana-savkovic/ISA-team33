package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityOrderDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyOrderDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;

public interface PharmacyOrderService {

	PharmacyOrder findById(int id);
	void addDrugQuantity(DrugQuantityOrderDTO drugQuantityDTO);
	void add(PharmacyOrderDTO pharmacyOrderDTO);
	int findByMaxId();
	Collection<DrugQuantityOrder> findByPharmacyOrderId(int id);
	Collection<PharmacyOrder> findByPharmacyId(int id);
}

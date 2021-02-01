package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityOrderDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;

public class DrugQuantityOrderMapper {

	public static Collection<DrugQuantityOrderDTO> toDrugQuantityOrderDTOs(Collection<DrugQuantityOrder> drugQuantityOrders){
		Collection<DrugQuantityOrderDTO> drugQuantityOrderDTOs = new ArrayList<>();
		for(DrugQuantityOrder d:drugQuantityOrders) {
			drugQuantityOrderDTOs.add(new DrugQuantityOrderDTO(d.getId(), d.getDrug().getId(), d.getQuantity(), d.getPharmacyOrder().getId(), d.getDrug().getName()));
		}
		return drugQuantityOrderDTOs;
	}
}

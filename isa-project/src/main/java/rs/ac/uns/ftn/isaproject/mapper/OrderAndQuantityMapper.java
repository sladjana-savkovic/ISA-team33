package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.OrderAndQuantityDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;

public class OrderAndQuantityMapper {

	public static Collection<OrderAndQuantityDTO> toOrderAndQuantityDTOs(Collection<PharmacyOrder> pharmacyOrders){
		Collection<OrderAndQuantityDTO> orderAndQuantityDTOs = new ArrayList<>();
		for(PharmacyOrder o : pharmacyOrders) {
			OrderAndQuantityDTO dto = new OrderAndQuantityDTO();
			dto.id = o.getId();
			dto.isFinished = o.isFinished();
			dto.limitDate = o.getLimitDate();
			dto.drugQuantityDTOs = DrugQuantityOrderMapper.toDrugQuantityOrderDTOs(o.getDrugQuantityOrders());
			orderAndQuantityDTOs.add(dto);
		}
		return orderAndQuantityDTOs;
	}
		
}

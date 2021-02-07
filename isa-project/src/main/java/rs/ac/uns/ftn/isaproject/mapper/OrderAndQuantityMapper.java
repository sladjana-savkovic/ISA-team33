package rs.ac.uns.ftn.isaproject.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.OrderAndQuantityDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;

public class OrderAndQuantityMapper {

	public static Collection<OrderAndQuantityDTO> toOrderAndQuantityDTOs(Collection<PharmacyOrder> pharmacyOrders){
		Collection<OrderAndQuantityDTO> orderAndQuantityDTOs = new ArrayList<>();
		for(PharmacyOrder o : pharmacyOrders) {
			if (o.getLimitDate().isBefore(LocalDate.now())) 
				continue;			
			OrderAndQuantityDTO dto = new OrderAndQuantityDTO();
			dto.id = o.getId();
			dto.isFinished = o.isFinished();
			dto.limitDate = o.getLimitDate();
			dto.pharmacyName = o.getPharmacyAdministrator().getPharmacy().getName();
			dto.drugQuantityDTOs = DrugQuantityOrderMapper.toDrugQuantityOrderDTOs(o.getDrugQuantityOrders());
			orderAndQuantityDTOs.add(dto);
		}
		return orderAndQuantityDTOs;
	}
		
}

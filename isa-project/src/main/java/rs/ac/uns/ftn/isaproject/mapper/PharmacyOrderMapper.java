package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.PharmacyOrderDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;

public class PharmacyOrderMapper {

	public static Collection<PharmacyOrderDTO> toPharmacyOrderDTOs(Collection<PharmacyOrder> pharmacyOrders){
		Collection<PharmacyOrderDTO> pharmacyOrderDTOs = new ArrayList<>();
		for(PharmacyOrder d:pharmacyOrders) {
			pharmacyOrderDTOs.add(new PharmacyOrderDTO(d.getId(), d.getLimitDate(), d.isFinished(), d.getPharmacyAdministrator().getId()));
		}
		return pharmacyOrderDTOs;
	}
}

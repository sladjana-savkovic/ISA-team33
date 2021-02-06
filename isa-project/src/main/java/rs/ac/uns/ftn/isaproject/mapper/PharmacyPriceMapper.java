package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.PharmacyPriceDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pricelist;

public class PharmacyPriceMapper {

	public static Collection<PharmacyPriceDTO> toPharmacyPriceDTO(Collection<Pricelist> pricelists) {
		Collection<PharmacyPriceDTO> pharmacyPriceDTOs = new ArrayList<>();
		for(Pricelist pricelist : pricelists) {
			PharmacyPriceDTO dto = new PharmacyPriceDTO();
			dto.pharmacyId = pricelist.getPharmacy().getId();
			dto.pharmacyName = pricelist.getPharmacy().getName();
			dto.price = pricelist.getPrice();
			pharmacyPriceDTOs.add(dto);
		}		
		return pharmacyPriceDTOs;
	}
	
}

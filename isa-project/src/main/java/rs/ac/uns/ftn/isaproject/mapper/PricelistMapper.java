package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.PricelistDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pricelist;

public class PricelistMapper {

	public static Collection<PricelistDTO> toPricelistDTOs(Collection<Pricelist> pricelists){
		Collection<PricelistDTO> pricelistDTOs = new ArrayList<>();
		for(Pricelist p:pricelists) {
			pricelistDTOs.add(new PricelistDTO(p.getId(), p.getStartDate(), p.getEndDate(), p.getPrice(), p.getPharmacy().getId(), p.getDrug().getId(), p.getDrug().getName()));
		}
		return pricelistDTOs;
	}
	
}

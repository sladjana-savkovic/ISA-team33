package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DrugOfferDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;

public class DrugOfferMapper {

	public static Collection<DrugOfferDTO> toDrugOfferDTOs(Collection<DrugOffer> drugOffers){
		Collection<DrugOfferDTO> drugOfferDTOs = new ArrayList<>();
		for(DrugOffer d:drugOffers) {
			drugOfferDTOs.add(new DrugOfferDTO(d.getId(), d.getTotalPrice(), d.isAccepted(), d.getLimitDate(),d.getPharmacyOrder().getId()));
		}
		return drugOfferDTOs;
	}
}

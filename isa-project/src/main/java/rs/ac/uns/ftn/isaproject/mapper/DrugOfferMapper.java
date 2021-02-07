package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DrugOfferAndOrderDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugOfferDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;

public class DrugOfferMapper {

	public static Collection<DrugOfferDTO> toDrugOfferDTOs(Collection<DrugOffer> drugOffers){
		Collection<DrugOfferDTO> drugOfferDTOs = new ArrayList<>();
		for(DrugOffer d:drugOffers) {
			drugOfferDTOs.add(new DrugOfferDTO(d.getId(), d.getTotalPrice(), d.getStatus(), d.getLimitDate(), d.getPharmacyOrder().getId()));
		}
		return drugOfferDTOs;
	}
	
	public static DrugOfferDTO toDrugOfferDTO(DrugOffer drugOffer){
		return new DrugOfferDTO(drugOffer.getId(), drugOffer.getTotalPrice(), drugOffer.getStatus(), drugOffer.getLimitDate(), drugOffer.getPharmacyOrder().getId());
	}	
	
	public static Collection<DrugOfferAndOrderDTO> toDrugOfferAndOrderDTOs(Collection<DrugOffer> drugOffers){
		Collection<DrugOfferAndOrderDTO> drugOfferAndOrderDTOs = new ArrayList<>();
		for(DrugOffer d : drugOffers) {
			drugOfferAndOrderDTOs.add(new DrugOfferAndOrderDTO(toDrugOfferDTO(d),  PharmacyOrderMapper.toPharmacyOrderDTO(d.getPharmacyOrder())));
		}
		return drugOfferAndOrderDTOs;
	}
	
}

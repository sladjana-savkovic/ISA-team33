package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityPharmacyDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;

public class DrugQuantityPharmacyMapper {

	public static Collection<DrugQuantityPharmacyDTO> toDrugQuantityPharmacyDTOs(Collection<DrugQuantityPharmacy> drugQuantityPharmacies){
		Collection<DrugQuantityPharmacyDTO> collection = new ArrayList<DrugQuantityPharmacyDTO>();
		
		for(DrugQuantityPharmacy quantityPharmacy:drugQuantityPharmacies) {
			collection.add(new DrugQuantityPharmacyDTO(quantityPharmacy.getDrug().getId(), quantityPharmacy.getDrug().getName()));
		}
		return collection;
	}
}

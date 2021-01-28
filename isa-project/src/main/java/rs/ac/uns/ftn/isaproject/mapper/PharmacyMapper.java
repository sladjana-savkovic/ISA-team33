package rs.ac.uns.ftn.isaproject.mapper;

import rs.ac.uns.ftn.isaproject.dto.PharmacyDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;

public class PharmacyMapper {

	public static PharmacyDTO toPharmacyDTO(Pharmacy pharmacy){
		
		return null;
		//return new PharmacyDTO(pharmacy.getId(), pharmacy.getName(), pharmacy.getAverageGrade(), pharmacy.getAddress(), pharmacy.getCity().getId(), pharmacy.getCity().getName());
		
	}
}

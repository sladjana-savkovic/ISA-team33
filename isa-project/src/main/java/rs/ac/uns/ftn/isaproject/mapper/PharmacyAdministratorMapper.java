package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.PharmacyAdministratorDTO;
import rs.ac.uns.ftn.isaproject.model.users.PharmacyAdministrator;

public class PharmacyAdministratorMapper {

	public static PharmacyAdministratorDTO toPharmacyAdministratorDTO(PharmacyAdministrator pharmacyAdministrator) {
		
		return new PharmacyAdministratorDTO(pharmacyAdministrator.getId(),pharmacyAdministrator.getName(),pharmacyAdministrator.getSurname(),pharmacyAdministrator.getDateOfBirth(),pharmacyAdministrator.getEmail(),pharmacyAdministrator.getPassword(),
				pharmacyAdministrator.getAddress(), pharmacyAdministrator.getCity().getId(),pharmacyAdministrator.getCity().getName(),
				pharmacyAdministrator.getCity().getCountry().getName(), pharmacyAdministrator.getPharmacy().getId());
	}
	
	public static Collection<PharmacyAdministratorDTO> toPharmacyAdministratorDTOs(Collection<PharmacyAdministrator> pharmacyAdministraotrs){
		Collection<PharmacyAdministratorDTO> pharmacyAdministratorDTOs = new ArrayList<>();
		for(PharmacyAdministrator pa:pharmacyAdministraotrs) {
			pharmacyAdministratorDTOs.add(new PharmacyAdministratorDTO(pa.getId(), pa.getName(), pa.getSurname(), pa.getDateOfBirth(), pa.getEmail(), pa.getPassword(), pa.getAddress(), pa.getCity().getId(), pa.getCity().getName(), pa.getCity().getCountry().getName(), pa.getPharmacy().getId()));
		}
		return pharmacyAdministratorDTOs;
	}
}

package rs.ac.uns.ftn.isaproject.service.users;

import rs.ac.uns.ftn.isaproject.dto.PharmacyAdministratorDTO;
import rs.ac.uns.ftn.isaproject.model.users.PharmacyAdministrator;

public interface PharmacyAdministratorService {

	void add(PharmacyAdministratorDTO pharmacyAdministratorDTO);
	PharmacyAdministrator getOne(int id);
	void updateInfo(PharmacyAdministratorDTO pharmacyAdministratorDTO);
	void updatePassword(int id, String password);
}

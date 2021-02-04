package rs.ac.uns.ftn.isaproject.service.users;

import rs.ac.uns.ftn.isaproject.dto.AddSystemAdministratorDTO;
import rs.ac.uns.ftn.isaproject.model.users.SystemAdministrator;

public interface SystemAdministratorService {

	SystemAdministrator getOne(int id);
	void add(AddSystemAdministratorDTO systemAdministratorDTO);
}

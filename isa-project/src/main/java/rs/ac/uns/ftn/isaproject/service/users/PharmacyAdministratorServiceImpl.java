package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.isaproject.repository.users.PharmacyAdministratorRepository;

public class PharmacyAdministratorServiceImpl implements PharmacyAdministratorService {

	private PharmacyAdministratorRepository administratorRepository;
	
	@Autowired
	public PharmacyAdministratorServiceImpl(PharmacyAdministratorRepository administratorRepository) {
		this.administratorRepository = administratorRepository;
	}
}

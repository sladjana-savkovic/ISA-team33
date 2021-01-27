package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.repository.users.PharmacyAdministratorRepository;

@Service
public class PharmacyAdministratorServiceImpl implements PharmacyAdministratorService {

	private PharmacyAdministratorRepository administratorRepository;
	
	@Autowired
	public PharmacyAdministratorServiceImpl(PharmacyAdministratorRepository administratorRepository) {
		this.administratorRepository = administratorRepository;
	}
}

package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.repository.users.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	private PatientRepository patientRepository;
	
	@Autowired
	public PatientServiceImpl(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
}

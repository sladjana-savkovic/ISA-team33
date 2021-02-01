package rs.ac.uns.ftn.isaproject.service.users;

import rs.ac.uns.ftn.isaproject.dto.AddPatientDTO;

public interface PatientService {
 
	void increasePenalty(int id);
	void add(AddPatientDTO addPatientDTO);
	
}

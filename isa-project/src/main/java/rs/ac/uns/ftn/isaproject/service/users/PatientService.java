package rs.ac.uns.ftn.isaproject.service.users;

import rs.ac.uns.ftn.isaproject.dto.AddPatientDTO;
import rs.ac.uns.ftn.isaproject.dto.PatientDTO;
import rs.ac.uns.ftn.isaproject.model.users.Patient;

public interface PatientService {
 
	void increasePenalty(int id);

	void add(AddPatientDTO addPatientDTO);
	
	boolean checkAllergyOnDrug(int id, int drugId);

	Patient getOne(int id);
	
	void updateInfo(PatientDTO patientDTO);
}

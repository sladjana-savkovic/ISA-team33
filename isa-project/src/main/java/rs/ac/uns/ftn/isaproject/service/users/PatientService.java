package rs.ac.uns.ftn.isaproject.service.users;


import org.springframework.mail.MailException;

import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.AddPatientDTO;
import rs.ac.uns.ftn.isaproject.dto.PatientDTO;
import rs.ac.uns.ftn.isaproject.model.users.Patient;

public interface PatientService {
 
	void increasePenalty(int id);

	void add(AddPatientDTO addPatientDTO) throws MailException, InterruptedException;
	
	boolean checkAllergyOnDrug(int id, int drugId);

	Patient getOne(int id);
	
	void updateInfo(PatientDTO patientDTO);
	
	Collection<Patient> findExaminedPatientsByDoctorId(int doctorId);
	
	Collection<Patient> findUnexaminedPatientsByDoctorId(int doctorId);
	
	Collection<PatientDTO> searchByNameAndSurname(String name, String surname,Collection<PatientDTO> patientDTOs);
	
}

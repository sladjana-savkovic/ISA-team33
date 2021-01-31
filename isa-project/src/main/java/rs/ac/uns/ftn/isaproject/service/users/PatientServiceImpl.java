package rs.ac.uns.ftn.isaproject.service.users;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.users.Patient;
import rs.ac.uns.ftn.isaproject.repository.users.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	private PatientRepository patientRepository;
	
	@Autowired
	public PatientServiceImpl(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Override
	public void increasePenalty(int id) {
		Patient patient = patientRepository.getOne(id);
		patient.setPenalty(patient.getPenalty() + 1);
		patientRepository.save(patient);
	}

	@Override
	public boolean checkAllergyOnDrug(int id, int drugId) {
		Collection<Drug> allergies = patientRepository.getOne(id).getAllergies();
		
		for(Drug drug:allergies) {
			if(drug.getId() == drugId) {
				return true;
			}
		}
		
		return false;
	}

}

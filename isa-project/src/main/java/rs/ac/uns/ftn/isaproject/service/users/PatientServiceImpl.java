package rs.ac.uns.ftn.isaproject.service.users;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddPatientDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.users.Patient;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;
import rs.ac.uns.ftn.isaproject.repository.users.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	private PatientRepository patientRepository;
	private CityRepository cityRepository;
	
	@Autowired
	public PatientServiceImpl(PatientRepository patientRepository, CityRepository cityRepository) {
		this.patientRepository = patientRepository;
		this.cityRepository = cityRepository;
	}

	@Override
	public void increasePenalty(int id) {
		Patient patient = patientRepository.getOne(id);
		patient.setPenalty(patient.getPenalty() + 1);
		patientRepository.save(patient);
	}

	@Override
	public void add(AddPatientDTO addPatientDTO) {
		Patient patient = new Patient();
		City city = cityRepository.getOne(addPatientDTO.cityId);
		
		patient.setName(addPatientDTO.name);
		patient.setSurname(addPatientDTO.surname);
		patient.setEmail(addPatientDTO.email);
		patient.setTelephone(addPatientDTO.telephone);
		patient.setPassword(addPatientDTO.password);
		patient.setAddress(addPatientDTO.address);
		patient.setCity(city);	
		patient.setDateOfBirth(addPatientDTO.dateOfBirth);
		patient.setActive(false);
		patient.setPenalty(0);
					
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

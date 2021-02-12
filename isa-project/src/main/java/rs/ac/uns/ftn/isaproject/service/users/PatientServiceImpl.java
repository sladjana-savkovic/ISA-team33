package rs.ac.uns.ftn.isaproject.service.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.AddPatientDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.PatientDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.users.Patient;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;
import rs.ac.uns.ftn.isaproject.repository.users.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	private PatientRepository patientRepository;
	private CityRepository cityRepository;
	private DrugRepository drugRepository;
	private UserAccountService userAccountService;
	@Autowired
	public PatientServiceImpl(PatientRepository patientRepository, CityRepository cityRepository, DrugRepository drugRepository, UserAccountService userAccountService) {
		this.patientRepository = patientRepository;
		this.cityRepository = cityRepository;
		this.drugRepository = drugRepository;
    this.userAccountService = userAccountService;
	}

	@Override
	public void increasePenalty(int id) {
		Patient patient = patientRepository.getOne(id);
		patient.setPenalty(patient.getPenalty() + 1);
		patientRepository.save(patient);
	}

	@Override
	public void add(AddPatientDTO addPatientDTO) throws MailException, InterruptedException, Exception {
		Patient patient = new Patient();
		
		City city = cityRepository.getOne(addPatientDTO.cityId);
		patient.setCity(city);		
		
		patient.setName(addPatientDTO.name);
		patient.setSurname(addPatientDTO.surname);
		patient.setTelephone(addPatientDTO.telephone);	
		patient.setAddress(addPatientDTO.address);		
		patient.setDateOfBirth(addPatientDTO.dateOfBirth);
		patient.setPenalty(0);
		
		userAccountService.save(addPatientDTO.email, addPatientDTO.password, "ROLE_PATIENT", false, patient);	
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

	@Override
	public Patient getOne(int id) {
		return patientRepository.getOne(id);
	}

	@Override
	public void updateInfo(PatientDTO patientDTO) {
		Patient patient = patientRepository.getOne(patientDTO.id);
		City city = cityRepository.getOne(patientDTO.cityId);
		
		patient.setName(patientDTO.name);
		patient.setSurname(patientDTO.surname);
		patient.setDateOfBirth(patientDTO.dateOfBirth);
		//patient.setPassword(patientDTO.password);
		patient.setTelephone(patientDTO.telephone);		
		patient.setAddress(patientDTO.address);
		patient.setCity(city);
		Set<Drug> drugs = new HashSet<Drug>();
		for(DrugDTO i : patientDTO.allergies) {
			Drug d= drugRepository.findById(i.id).get();
			drugs.add(d);
		}
		patient.setAllergies(drugs);
		
		patientRepository.save(patient);
		
	}

	@Override
	public Collection<Patient> findExaminedPatientsByDoctorId(int doctorId) {
		Collection<Integer> examinedPatientIds = new ArrayList<Integer>();
		Collection<Patient> examinedPatients = new ArrayList<Patient>();
		
		for(Patient p:patientRepository.findExaminedPatientsByDoctorId(doctorId)) {
			if(!examinedPatientIds.contains(p.getId())) {
				examinedPatientIds.add(p.getId());
				examinedPatients.add(p);
			}
		}
		
		return examinedPatients;
	}

	@Override
	public Collection<Patient> findUnexaminedPatientsByDoctorId(int doctorId) {
		Collection<Patient> examinedPatients = findExaminedPatientsByDoctorId(doctorId);
		Collection<Patient> patientsHaveAppointments = patientRepository.findPatientsHaveAppointmentByDoctorId(doctorId);
		Collection<Integer> examinedPatientIds = new ArrayList<Integer>();
		Collection<Patient> unexaminedPatients = new ArrayList<Patient>();
		
		for(Patient p:examinedPatients) {
			if(!examinedPatientIds.contains(p.getId()))
				examinedPatientIds.add(p.getId());
		}
		
		for(Patient p:patientsHaveAppointments) {
			if(!examinedPatientIds.contains(p.getId())) {
				unexaminedPatients.add(p);
				examinedPatientIds.add(p.getId());
			}
		}
		
		return unexaminedPatients;
	}

	@Override
	public Collection<PatientDTO> searchByNameAndSurname(String name, String surname, Collection<PatientDTO> patientDTOs) {
		Collection<PatientDTO> searchResult = new ArrayList<>();
		
		if(name.equals("&")) name = "";
		if(surname.equals("&")) surname = "";
		
		for(PatientDTO patient:patientDTOs) {
			if(patient.name.toLowerCase().contains(name.toLowerCase()) && patient.surname.toLowerCase().contains(surname.toLowerCase())) {
				searchResult.add(patient);
			}
		}
		return searchResult;
	}

}

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

	/*@Override
	public Collection<Patient> findAllExaminedByDoctor(int doctorId) {
		Collection<Patient> patients = patientRepository.findAllPatients();
		Collection<Patient> examinedPatients = new ArrayList<>();
		
		for (Patient patient : patients) {
			Collection<ExaminationReport> examinationReports = patient.getExaminationReports();
			
			for (ExaminationReport report : examinationReports) {
				if(report.getAppointment().getDoctor().getId() == doctorId) {
					examinedPatients.add(patient);
					break;
				}
			}
		}
		
		return examinedPatients;
	}*/
}

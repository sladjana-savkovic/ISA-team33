package rs.ac.uns.ftn.isaproject.mapper;

import rs.ac.uns.ftn.isaproject.dto.PatientDTO;
import rs.ac.uns.ftn.isaproject.model.users.Patient;

public class PatientMapper {

	public static PatientDTO toPatientDTO(Patient patient) {
		
		return new PatientDTO(
				patient.getId(), 
				patient.getName(), 
				patient.getSurname(), 
				patient.getDateOfBirth(), 
				patient.getEmail(), 
				patient.getPassword(),
				patient.getCity().getCountry().getId(), 
				patient.getCity().getCountry().getName(),
			    patient.getCity().getId(),
				patient.getCity().getName(), 
				patient.getAddress(),
				patient.getTelephone(),
				patient.getAllergies());
	}
	
}

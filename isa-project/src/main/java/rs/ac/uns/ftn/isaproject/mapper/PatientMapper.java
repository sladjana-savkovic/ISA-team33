package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.PatientDTO;
import rs.ac.uns.ftn.isaproject.model.users.Patient;

public class PatientMapper {

	public static PatientDTO toPatientDTO(Patient patient) {
		Collection<DrugDTO> allergies = DrugMapper.toDrugDTOs(patient.getAllergies());
		
		return new PatientDTO(
				patient.getId(), 
				patient.getName(), 
				patient.getSurname(), 
				patient.getDateOfBirth(), 
				null, 
				null,
				patient.getCity().getCountry().getId(), 
				patient.getCity().getCountry().getName(),
			    patient.getCity().getId(),
				patient.getCity().getName(), 
				patient.getAddress(),
				patient.getTelephone(),
				allergies);
	}
	
	public static Collection<PatientDTO> toPatientDTOs(Collection<Patient> patients) {
		Collection<PatientDTO> patientDTOs = new ArrayList<PatientDTO>();
		
		for(Patient patient:patients) {
			Collection<DrugDTO> allergies = DrugMapper.toDrugDTOs(patient.getAllergies());
			patientDTOs.add(new PatientDTO(
				patient.getId(), 
				patient.getName(), 
				patient.getSurname(), 
				patient.getDateOfBirth(), 
				patient.getCity().getCountry().getName(),
				patient.getCity().getName(), 
				patient.getAddress(),
				patient.getTelephone(),
				allergies));
		}
		
		return patientDTOs;
	}
	
}

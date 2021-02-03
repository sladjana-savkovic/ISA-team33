package rs.ac.uns.ftn.isaproject.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
				patient.getEmail(), 
				patient.getPassword(),
				patient.getCity().getCountry().getId(), 
				patient.getCity().getCountry().getName(),
			    patient.getCity().getId(),
				patient.getCity().getName(), 
				patient.getAddress(),
				patient.getTelephone(),
				allergies);
	}
	
}

package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.users.Patient;

public class ExaminedPatientMapper {

	public static Collection<ExaminedPatientDTO> toExaminedPatientDTOs(Collection<ExaminationReport> examinationReports){
		Collection<ExaminedPatientDTO> examinedPatientDTOs = new ArrayList<>();
		Patient patient;
		Collection<String> patientAllergies;
		
		for(ExaminationReport report:examinationReports) {
			patient = report.getAppointment().getPatient();
			patientAllergies = new ArrayList<String>();
			for(Drug drug : patient.getAllergies()) { patientAllergies.add(drug.getName()); }
			examinedPatientDTOs.add(new ExaminedPatientDTO(patient.getId(),patient.getName(),
					patient.getSurname(),patient.getDateOfBirth(), patient.getAddress() + ", " + patient.getCity().getName(),
					patientAllergies, report.getAppointment().getStartTime().toLocalDate().toString()));
		}
		return examinedPatientDTOs;
	}
	
	public static Collection<ExaminedPatientDTO> toExaminedPatientDTOsFromAppointments(Collection<Appointment> appointments){
		Collection<ExaminedPatientDTO> unExaminedPatientDTOs = new ArrayList<>();
		Collection<String> patientAllergies;
		
		for(Appointment appointment:appointments) {
			patientAllergies = new ArrayList<String>();
			for(Drug drug : appointment.getPatient().getAllergies()) { patientAllergies.add(drug.getName()); }
			unExaminedPatientDTOs.add(new ExaminedPatientDTO(appointment.getPatient().getId(),appointment.getPatient().getName(),
					appointment.getPatient().getSurname(),appointment.getPatient().getDateOfBirth(), appointment.getPatient().getAddress() + ", " + 
					appointment.getPatient().getCity().getName(),patientAllergies, "-"));
		}
		
		return unExaminedPatientDTOs;
	}
	
	public static Collection<Integer> toPatientIds(Collection<ExaminedPatientDTO> examinedPatientDTOs){
		Collection<Integer> patientIds = new ArrayList<Integer>();
		
		for(ExaminedPatientDTO examinedPatientDTO:examinedPatientDTOs) {
			if(!patientIds.contains(examinedPatientDTO.id)) {
				patientIds.add(examinedPatientDTO.id);
			}
		}
		
		return patientIds;
	}
}

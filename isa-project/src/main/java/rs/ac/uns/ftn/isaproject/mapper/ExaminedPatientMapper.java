package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
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
					patientAllergies, report.getAppointment().getStartTime().toLocalDate()));
		}
		return examinedPatientDTOs;
	}
}

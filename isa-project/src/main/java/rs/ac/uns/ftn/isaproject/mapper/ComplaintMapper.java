package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.ComplaintDTO;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintDoctor;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintPharmacy;


public class ComplaintMapper {

	public static Collection<ComplaintDTO> toComplaintDTOs(Collection<ComplaintDoctor> complaintsDoctor, Collection<ComplaintPharmacy> complaintsPharmacy){
		Collection<ComplaintDTO> complaintDTOs = new ArrayList<>();
		
		for(ComplaintDoctor c : complaintsDoctor) {
			ComplaintDTO dto = new ComplaintDTO();
			dto.complaintId = c.getId();
			dto.content = c.getContent();
			dto.patientId = c.getPatient().getUser().getId();
			dto.patientNameAndSurname = c.getPatient().getUser().getName() + " " + c.getPatient().getUser().getSurname();
			dto.patientEmail = c.getPatient().getUsername();
			dto.subjectName = c.getDoctor().getUser().getName() + " " + c.getDoctor().getUser().getSurname();
			dto.subjectId = c.getDoctor().getUser().getId();	
			dto.subjectType = "Doctor";
			complaintDTOs.add(dto);			
		}
		for(ComplaintPharmacy c : complaintsPharmacy) {
			ComplaintDTO dto = new ComplaintDTO();
			dto.complaintId = c.getId();
			dto.content = c.getContent();
			dto.patientId = c.getPatient().getUser().getId();
			dto.patientNameAndSurname = c.getPatient().getUser().getName() + " " + c.getPatient().getUser().getSurname();
			dto.patientEmail = c.getPatient().getUsername();
			dto.subjectName = c.getPharmacy().getName();
			dto.subjectId = c.getPharmacy().getId();
			dto.subjectType = "Pharmacy";
			complaintDTOs.add(dto);			
		}		
		return complaintDTOs;
	}
	
}

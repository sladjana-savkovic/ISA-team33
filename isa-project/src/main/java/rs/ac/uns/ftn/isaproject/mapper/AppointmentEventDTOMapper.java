package rs.ac.uns.ftn.isaproject.mapper;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AppointmentEventDTO;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public class AppointmentEventDTOMapper {

	public static Collection<AppointmentEventDTO> toAppointmentEventDTOs(Collection<Appointment> appointments){
		Collection<AppointmentEventDTO> appointmentEventDTOs = new ArrayList<>();
		String url = "";
		long minutes;
		String title;
		
		for(Appointment a : appointments) {
			
			url = "";
			minutes = ChronoUnit.MINUTES.between(a.getStartTime(), a.getEndTime());
			title = "(" + minutes + "min)"; 
			
			if(a.getDoctor().getTypeOfDoctor() == TypeOfDoctor.Dermatologist) {
				title = a.getPharmacy().getName() + title;
			}
			
			if(a.getStatus() == AppointmentStatus.Created) {
				appointmentEventDTOs.add(new AppointmentEventDTO(title, a.getStartTime().toString(), a.getEndTime().toString(), url, "#03ff11"));
			}
			
			if(a.getStatus() == AppointmentStatus.Scheduled) {
				if(a.getDoctor().getTypeOfDoctor() == TypeOfDoctor.Dermatologist) {
					title = a.getPatient().getName() + " " + a.getPatient().getSurname() + ", " + title; 
				}
				else {
					title = a.getPatient().getName() + " " + a.getPatient().getSurname() + " " + title; 
				}
				url = "http://localhost:8080/html/doctor/start_examination.html?" + a.getId();
				appointmentEventDTOs.add(new AppointmentEventDTO(title, a.getStartTime().toString(), a.getEndTime().toString(), url, "#fe0202"));
			}
			
			if(a.getStatus() == AppointmentStatus.Canceled) {
				appointmentEventDTOs.add(new AppointmentEventDTO(title, a.getStartTime().toString(), a.getEndTime().toString(), url, "#dfff03"));
			}
		}
		
		return appointmentEventDTOs;
	}
}

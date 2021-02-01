package rs.ac.uns.ftn.isaproject.mapper;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AppointmentEventDTO;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public class AppointmentEventDTOMapper {

	public static Collection<AppointmentEventDTO> toAppointmentEventDTOs(Collection<Appointment> appointments){
		Collection<AppointmentEventDTO> appointmentEventDTOs = new ArrayList<>();
		
		for(Appointment a : appointments) {
			if(a.getStatus() == AppointmentStatus.Scheduled) {
				long minutes = ChronoUnit.MINUTES.between(a.getStartTime(), a.getEndTime());
				String patient = a.getPatient().getName() + " " + a.getPatient().getSurname() + " (" + minutes + "min)"; 
				String url = "http://localhost:8080/html/doctor/start_examination.html?" + a.getId();
				appointmentEventDTOs.add(new AppointmentEventDTO(patient, a.getStartTime().toString(), a.getEndTime().toString(), url));
			}
		}
		
		return appointmentEventDTOs;
	}
}

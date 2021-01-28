package rs.ac.uns.ftn.isaproject.mapper;

import java.util.HashSet;
import java.util.Set;

import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public class AppointmentMapper {

	public static Set<AppointmentDTO> toAppointmentDTOs(Set<Appointment> appointments){
		
		Set<AppointmentDTO> appointmentDTOs = new HashSet<AppointmentDTO>();
		
		for(Appointment a:appointments) {
			
			appointmentDTOs.add(new AppointmentDTO(a.getId(), a.getTypeOfAppointment().toString(), a.getStartTime().toString(), a.getEndTime().toString(), a.getPrice(), a.getPharmacy().getId()));
		}
		
		return appointmentDTOs;
	}
}

package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public class AppointmentMapper {

	public static Collection<AppointmentDTO> toAppointmentDTOs(Collection<Appointment> appointments){
		
		Collection<AppointmentDTO> appointmentDTOs = new ArrayList<>();
		
		for(Appointment a:appointments) {
			
			appointmentDTOs.add(new AppointmentDTO(a.getId(), a.getTypeOfAppointment().toString(), a.getStartTime().toString(), a.getEndTime().toString(), a.getPrice(), a.getPharmacy().getId()));
		}
		return appointmentDTOs;
	}
}

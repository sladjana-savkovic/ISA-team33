package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentForExaminationDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;

public class AppointmentMapper {

	public static Collection<AppointmentDTO> toAppointmentDTOs(Collection<Appointment> appointments){
		
		Collection<AppointmentDTO> appointmentDTOs = new ArrayList<>();
		
		for(Appointment a:appointments) {
			
			appointmentDTOs.add(new AppointmentDTO(a.getId(), a.getStartTime().toString(), a.getEndTime().toString(), a.getPrice(), a.getPharmacy().getId(), a.getStatus(), a.getDoctor().getSurname(), a.getDoctor().getTypeOfDoctor()));
		}
		return appointmentDTOs;
	}
	
	public static AppointmentForExaminationDTO toAppointmentForExaminationDTO(Appointment appointment) {
		
		String status = appointment.getStatus().name();
		
		return new AppointmentForExaminationDTO(appointment.getId(),appointment.getPatient().getId(),appointment.getPatient().getName(),
					appointment.getPatient().getSurname(),appointment.getDoctor().getId(),appointment.getDoctor().getName(),
					appointment.getDoctor().getSurname(),appointment.getPharmacy().getId(),appointment.getPharmacy().getName(),
					appointment.getStartTime(),appointment.getEndTime(),appointment.getPrice(),status);
	}
	
	public static Collection<AddAppointmentDTO> toAddAppointmentDTOs(Collection<Appointment> appointments){
		
		Collection<AddAppointmentDTO> appointmentDTOs = new ArrayList<>();
		
		for(Appointment a:appointments) {
			int patientId = a.getPatient() == null ? -1 : a.getPatient().getId();
			AddAppointmentDTO appointment = new AddAppointmentDTO(a.getId(), a.getStartTime().toString(), a.getEndTime().toString(), a.getDoctor().getId(), a.getPharmacy().getId(), a.getPrice(), patientId);
			appointment.averageGrade = a.getDoctor().getAverageGrade();
			appointmentDTOs.add(appointment);
		}
		return appointmentDTOs;
	}
}

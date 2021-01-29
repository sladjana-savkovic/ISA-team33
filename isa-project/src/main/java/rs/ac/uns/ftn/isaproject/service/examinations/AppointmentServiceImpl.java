package rs.ac.uns.ftn.isaproject.service.examinations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.repository.examinations.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	private AppointmentRepository appointmentRepository;
	
	@Autowired
	public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public void changeStatus(int id, AppointmentStatus status) {
		Appointment appointment = appointmentRepository.getOne(id);
		appointment.setStatus(status);
		appointmentRepository.save(appointment);
	}
}

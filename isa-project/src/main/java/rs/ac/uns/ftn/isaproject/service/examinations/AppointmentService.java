package rs.ac.uns.ftn.isaproject.service.examinations;

import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;

public interface AppointmentService {

	void changeStatus(int id, AppointmentStatus status);
}

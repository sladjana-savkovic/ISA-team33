package rs.ac.uns.ftn.isaproject.service.users;

import java.time.LocalTime;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.WorkingTimeDTO;
import rs.ac.uns.ftn.isaproject.model.users.WorkingTime;

public interface WorkingTimeService {

	void add(WorkingTimeDTO workingTimeDTO);
	Collection<WorkingTime> findByPharmacyId(int id);
	boolean isDoctorWorkInPharmacy(int id_pharmacy, int id_doctor, String start_time, String end_time);
	boolean checkIfDoctorWorkInPharmacy(int pharmacyId, int doctorId, LocalTime startTime, LocalTime endTime);
}

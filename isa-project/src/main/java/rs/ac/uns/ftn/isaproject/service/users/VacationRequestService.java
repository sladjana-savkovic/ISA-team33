package rs.ac.uns.ftn.isaproject.service.users;

import java.time.LocalDate;
import java.util.Collection;
import rs.ac.uns.ftn.isaproject.dto.AddVacationRequestDTO;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.model.users.VacationRequest;

public interface VacationRequestService {

	void add(AddVacationRequestDTO vacationRequestDTO);
	void acceptRequest(int id);
	void rejectRequest(int id, String reason);
	Collection<VacationRequest> findCreatedByPharmacyId(int id);
	boolean isDoctorOnVacation(int doctorId, int pharmacyId, LocalDate date);
	Doctor findDoctorById(int id);
}

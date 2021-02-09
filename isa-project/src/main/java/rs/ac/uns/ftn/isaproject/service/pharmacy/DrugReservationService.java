package rs.ac.uns.ftn.isaproject.service.pharmacy;

import rs.ac.uns.ftn.isaproject.dto.DrugReservationDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;

public interface DrugReservationService {

	DrugReservation searchOne(int id, int doctorId);
	DrugReservation confirmReservation(int id);
	void cancelReservation(int id) throws Exception;
	DrugReservation createReservation(DrugReservationDTO drugReservastionDTO) throws Exception;
}

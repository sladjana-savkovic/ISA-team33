package rs.ac.uns.ftn.isaproject.service.pharmacy;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;

public interface DrugReservationService {

	DrugReservation searchOne(int id, int doctorId);
	DrugReservation confirmReservation(int id);
}

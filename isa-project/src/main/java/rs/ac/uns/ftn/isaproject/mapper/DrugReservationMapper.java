package rs.ac.uns.ftn.isaproject.mapper;

import rs.ac.uns.ftn.isaproject.dto.DrugReservationDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;

public class DrugReservationMapper {

	public static DrugReservationDTO toDrugReservationDTO (DrugReservation drugReservation) {
		return new DrugReservationDTO(drugReservation.getId(),drugReservation.getDrug().getId(),drugReservation.getDrug().getName(),
									  drugReservation.getDateLimit(),drugReservation.isDone(),drugReservation.getPatient().getId(),
									  drugReservation.getPatient().getName(),drugReservation.getPatient().getSurname());
	}
}

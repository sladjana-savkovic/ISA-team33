package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DrugReservationDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;

public class DrugReservationMapper {

	public static DrugReservationDTO toDrugReservationDTO (DrugReservation drugReservation) {
		return new DrugReservationDTO(drugReservation.getId(),drugReservation.getDrug().getId(),drugReservation.getDrug().getName(),
									  drugReservation.getDateLimit(),drugReservation.isDone(),drugReservation.getPatient().getId(),
									  drugReservation.getPatient().getName(),drugReservation.getPatient().getSurname(),
									  drugReservation.getPharmacy().getId(), drugReservation.getPharmacy().getName());
	}
	public static Collection<DrugReservationDTO> toDrugReservationDTOs(Collection<DrugReservation> drugs){
			
			Collection<DrugReservationDTO> drugsDTOs = new ArrayList<>();
			
			for(DrugReservation d:drugs) {
				
				drugsDTOs.add(new DrugReservationDTO(d.getId(),d.getDrug().getId(),d.getDrug().getName(),
						d.getDateLimit(),d.isDone(),d.getPatient().getId(),d.getPatient().getName(),
						d.getPatient().getSurname(),d.getPharmacy().getId(),d.getPharmacy().getName()));
			}
			return drugsDTOs;
	}
}

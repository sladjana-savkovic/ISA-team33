package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.ViewVacationRequestDTO;
import rs.ac.uns.ftn.isaproject.model.users.VacationRequest;

public class VacationRequestMapper {
	
	public static Collection<ViewVacationRequestDTO> toViewVacationRequestDTOs(Collection<VacationRequest> vacationRequests){
		Collection<ViewVacationRequestDTO> vacationRequestDTOs = new ArrayList<>();
		for(VacationRequest v:vacationRequests) {
			vacationRequestDTOs.add(new ViewVacationRequestDTO(v.getId(), v.getDoctor().getId(), v.getDoctor().getSurname(), v.getDoctor().getTypeOfDoctor().toString(), v.getStartDate(), v.getEndDate(), v.getPharmacy().getId(), v.getPharmacy().getName()));
		}
		return vacationRequestDTOs;
	}
}

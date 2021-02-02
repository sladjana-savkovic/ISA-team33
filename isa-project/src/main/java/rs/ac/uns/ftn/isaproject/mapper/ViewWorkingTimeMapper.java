package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.ViewWorkingTimeDTO;
import rs.ac.uns.ftn.isaproject.model.users.WorkingTime;

public class ViewWorkingTimeMapper {

	public static Collection<ViewWorkingTimeDTO> toViewWorkingTimeDTOs(Collection<WorkingTime> workingTimes){
		Collection<ViewWorkingTimeDTO> viewWorkingTimeDTOs = new ArrayList<>();
		for(WorkingTime w:workingTimes) {
			viewWorkingTimeDTOs.add(new ViewWorkingTimeDTO(w.getId(), w.getDoctor().getId(), w.getPharmacy().getId(), w.getStartTime(), w.getEndTime(), w.getDoctor().getName(), w.getDoctor().getSurname(), w.getDoctor().getTypeOfDoctor()));
		}
		return viewWorkingTimeDTOs;
	}
	
}

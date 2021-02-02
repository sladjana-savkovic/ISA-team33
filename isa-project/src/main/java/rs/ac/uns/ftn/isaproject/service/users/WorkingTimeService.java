package rs.ac.uns.ftn.isaproject.service.users;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.WorkingTimeDTO;
import rs.ac.uns.ftn.isaproject.model.users.WorkingTime;

public interface WorkingTimeService {

	void add(WorkingTimeDTO workingTimeDTO);
	Collection<WorkingTime> findByPharmacyId(int id);
}

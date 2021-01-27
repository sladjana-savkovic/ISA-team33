package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.repository.users.WorkingTimeRepository;

@Service
public class WorkingTimeServiceImpl implements WorkingTimeService {

	private WorkingTimeRepository workingTimeRepository;
	
	@Autowired
	public WorkingTimeServiceImpl(WorkingTimeRepository workingTimeRepository) {
		this.workingTimeRepository = workingTimeRepository;
	}
}

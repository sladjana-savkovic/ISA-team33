package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.repository.users.VacationRequestRepository;

@Service
public class VacationRequestServiceImpl implements VacationRequestService {

	private VacationRequestRepository vacationRepository;
	
	@Autowired
	public VacationRequestServiceImpl(VacationRequestRepository vacationRepository) {
		this.vacationRepository = vacationRepository;
	}
}

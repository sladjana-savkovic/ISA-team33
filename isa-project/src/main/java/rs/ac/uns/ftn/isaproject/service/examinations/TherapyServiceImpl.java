package rs.ac.uns.ftn.isaproject.service.examinations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.repository.examinations.TherapyRepository;

@Service
public class TherapyServiceImpl implements TherapyService {

	private TherapyRepository therapyRepository;
	
	@Autowired
	public TherapyServiceImpl(TherapyRepository therapyRepository) {
		this.therapyRepository = therapyRepository;
	}
}

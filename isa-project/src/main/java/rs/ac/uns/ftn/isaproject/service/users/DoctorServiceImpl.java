package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

	private DoctorRepository doctorRepository;
	
	@Autowired
	public DoctorServiceImpl(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}
}

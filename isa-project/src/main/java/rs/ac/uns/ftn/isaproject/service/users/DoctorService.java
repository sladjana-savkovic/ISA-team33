package rs.ac.uns.ftn.isaproject.service.users;

import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public interface DoctorService {

	Doctor getOne(int id);
	
	void updateInfo(DoctorDTO doctorDTO);
	void updatePassword(int id, String password);
}

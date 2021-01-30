package rs.ac.uns.ftn.isaproject.service.users;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.FilterDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.SearchDoctorDTO;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public interface DoctorService {

	Doctor getOne(int id);
	void updateInfo(DoctorDTO doctorDTO);
	void updatePassword(int id, String password);
	Collection<Doctor> findByPharmacyId(int id);
	Collection<Doctor> searchDoctors(SearchDoctorDTO searchDoctorDTO);
	Collection<Doctor> filterDoctors(FilterDoctorDTO filterDoctorDTO);
}

package rs.ac.uns.ftn.isaproject.service.users;

import java.util.Collection;
import java.util.Map;

import rs.ac.uns.ftn.isaproject.dto.AddDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.ViewSearchedDoctorDTO;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public interface DoctorService {

	Doctor getOne(int id);
	void updateInfo(DoctorDTO doctorDTO);
	void updatePassword(int id, String password);
	void add(AddDoctorDTO doctorDTO);
	Collection<Doctor> findByPharmacyId(int id);
	Collection<ViewSearchedDoctorDTO> searchByNameAndSurname(String name, String surname, Collection<ViewSearchedDoctorDTO> doctorDTOs);
	Collection<ViewSearchedDoctorDTO> filterByGradeAndType(String typeOfDoctor, int grade, Collection<ViewSearchedDoctorDTO> doctorDTOs);
	void deleteDoctor(int id);
	Collection<Doctor> getDoctorWithoutWorkingTime(int id);
	Collection<Map<String, Object>> report(int idPharmacy);
}

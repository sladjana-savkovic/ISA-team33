package rs.ac.uns.ftn.isaproject.service.users;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.mail.MailException;

import rs.ac.uns.ftn.isaproject.dto.AddDermatologistDTO;
import rs.ac.uns.ftn.isaproject.dto.AddDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.ViewSearchedDoctorDTO;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public interface DoctorService {

	Doctor getOne(int id);
	void updateInfo(DoctorDTO doctorDTO);
	void addPharmacist(AddDoctorDTO doctorDTO) throws MailException, InterruptedException, Exception;
	void add(AddDermatologistDTO dermatologistDTO) throws MailException, InterruptedException, Exception;
	Collection<Doctor> findByPharmacyId(int id);
	Collection<ViewSearchedDoctorDTO> searchByNameAndSurname(String name, String surname, Collection<ViewSearchedDoctorDTO> doctorDTOs);
	Collection<ViewSearchedDoctorDTO> filterByGradeAndType(String typeOfDoctor, int grade, Collection<ViewSearchedDoctorDTO> doctorDTOs);
	void deleteDoctor(int id);
	Collection<Doctor> getDoctorWithoutWorkingTime(int id);
	void addDermatologistInPharmacy(int id, int idPharmacy);
	Collection<Doctor> findDoctorNotInPharmacy(int id);
	Collection<Doctor> findAvailableDoctor(LocalDateTime date,Long idPharmacy);
	Collection<Doctor> findDoctorByType(int type);
	Collection<Doctor> getAllDoctors();
}

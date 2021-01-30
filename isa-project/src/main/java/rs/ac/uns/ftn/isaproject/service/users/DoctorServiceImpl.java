package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

	private DoctorRepository doctorRepository;
	private CityRepository cityRepository;
	
	@Autowired
	public DoctorServiceImpl(DoctorRepository doctorRepository, CityRepository cityRepository) {
		this.doctorRepository = doctorRepository;
		this.cityRepository = cityRepository;
	}

	@Override
	public Doctor getOne(int id) {
		return doctorRepository.getOne(id);
	}

	@Override
	public void updateInfo(DoctorDTO doctorDTO) {
		Doctor doctor = doctorRepository.getOne(doctorDTO.id);
		City city = cityRepository.getOne(doctorDTO.cityId);
		
		doctor.setName(doctorDTO.name);
		doctor.setSurname(doctorDTO.surname);
		doctor.setDateOfBirth(doctorDTO.dateOfBirth);
		doctor.setAddress(doctorDTO.address);
		doctor.setCity(city);
		doctor.setPassword(doctorDTO.password);
		
		doctorRepository.save(doctor);
	}

	@Override
	public void updatePassword(int id, String password) {
		Doctor doctor = doctorRepository.getOne(id);
		doctor.setPassword(password);
		doctorRepository.save(doctor);
	}

	@Override
	public void add(DoctorDTO doctorDTO) {
		Doctor doctor = doctorRepository.getOne(doctorDTO.id);
		City city = cityRepository.getOne(doctorDTO.cityId);
		
		doctor.setName(doctorDTO.name);
		doctor.setSurname(doctorDTO.surname);
		doctor.setDateOfBirth(doctorDTO.dateOfBirth);
		doctor.setEmail(doctorDTO.email);
		doctor.setAddress(doctorDTO.address);
		doctor.setCity(city);
		doctor.setPassword(doctorDTO.password);
		doctor.setTypeOfDoctor(TypeOfDoctor.valueOf(doctorDTO.typeOfDoctor));
		
		doctorRepository.save(doctor);
		
	}
}

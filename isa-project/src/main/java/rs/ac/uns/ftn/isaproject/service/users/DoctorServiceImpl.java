package rs.ac.uns.ftn.isaproject.service.users;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.dto.FilterDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.SearchDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.ViewSearchedDoctorDTO;
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
		Doctor doctor = new Doctor();
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
	
	public Collection<Doctor> findByPharmacyId(int id) {
		return doctorRepository.findByPharmacyId(id);
	}
	
	@Override
	public Collection<ViewSearchedDoctorDTO> searchDoctors(SearchDoctorDTO searchDoctorDTO){
		Collection<ViewSearchedDoctorDTO> doctors = searchDoctorDTO.doctors;
		Collection<ViewSearchedDoctorDTO> searchedDoctors = new ArrayList<ViewSearchedDoctorDTO>();
		for(ViewSearchedDoctorDTO d:doctors) {
			if(d.name.toLowerCase().contains(searchDoctorDTO.name.toLowerCase()) || d.surname.toLowerCase().contains(searchDoctorDTO.surname.toLowerCase())) {
				searchedDoctors.add(d);
			}
		}
		return searchedDoctors;
		
	}

	@Override
	public Collection<ViewSearchedDoctorDTO> filterDoctors(FilterDoctorDTO filterDoctorDTO) {
		Collection<ViewSearchedDoctorDTO> doctors = filterDoctorDTO.doctors;
		Collection<ViewSearchedDoctorDTO> filteredDoctors = new ArrayList<ViewSearchedDoctorDTO>();
		
		for(ViewSearchedDoctorDTO d:doctors) {
			if(d.grade == filterDoctorDTO.grade || d.typeOfDoctor == filterDoctorDTO.typeOfDoctor) {
				filteredDoctors.add(d);
			}
		}
		return filteredDoctors;
	}
}

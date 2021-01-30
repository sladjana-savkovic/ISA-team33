package rs.ac.uns.ftn.isaproject.service.users;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.FilterDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.SearchDoctorDTO;
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
	public Collection<Doctor> findByPharmacyId(int id) {
		return doctorRepository.findByPharmacyId(id);
	}
	
	public Collection<Doctor> searchDoctors(Collection<Doctor> doctors, SearchDoctorDTO searchDoctorDTO){
		//Collection<Doctor> doctors = findByPharmacyId(searchDoctorDTO.pharmacyId);
		Collection<Doctor> searchedDoctors = new ArrayList<Doctor>();
		for(Doctor d:doctors) {
			if(d.getName().toLowerCase().contains(searchDoctorDTO.name.toLowerCase()) || d.getSurname().toLowerCase().contains(searchDoctorDTO.surname.toLowerCase())) {
				searchedDoctors.add(d);
			}
		}
		return searchedDoctors;
		
	}

	@Override
	public Collection<Doctor> filterDoctors(Collection<Doctor> doctors, FilterDoctorDTO filterDoctorDTO) {
		//Collection<Doctor> doctors = findByPharmacyId(filterDoctorDTO.pharmacyId);
		Collection<Doctor> filteredDoctors = new ArrayList<Doctor>();
		
		for(Doctor d:doctors) {
			if(d.getAverageGrade() == filterDoctorDTO.grade || d.getTypeOfDoctor() == filterDoctorDTO.typeOfDoctor) {
				filteredDoctors.add(d);
			}
		}
		
		return filteredDoctors;
	}
}

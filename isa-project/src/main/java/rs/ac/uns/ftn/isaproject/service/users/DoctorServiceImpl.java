package rs.ac.uns.ftn.isaproject.service.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.AddDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.dto.ViewSearchedDoctorDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

	private DoctorRepository doctorRepository;
	private CityRepository cityRepository;
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	public DoctorServiceImpl(DoctorRepository doctorRepository, CityRepository cityRepository, PharmacyRepository pharmacyRepository) {
		this.doctorRepository = doctorRepository;
		this.cityRepository = cityRepository;
		this.pharmacyRepository = pharmacyRepository;
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
		doctor.setTelephone(doctorDTO.phoneNumber);
		doctor.setAddress(doctorDTO.address);
		doctor.setCity(city);
		
		doctorRepository.save(doctor);
	}

	@Override
	public void add(AddDoctorDTO doctorDTO) {
		Doctor doctor = new Doctor();
		City city = cityRepository.getOne(doctorDTO.cityId);
		Set<Pharmacy> pharmacies = new HashSet<Pharmacy>();
		Pharmacy pharmacy = pharmacyRepository.getOne(doctorDTO.pharmacyId);
		pharmacies.add(pharmacy);
		
		doctor.setName(doctorDTO.name);
		doctor.setSurname(doctorDTO.surname);
		doctor.setDateOfBirth(doctorDTO.dateOfBirth);
		doctor.setEmail(doctorDTO.email);
		doctor.setAddress(doctorDTO.address);
		doctor.setCity(city);
		doctor.setPassword(doctorDTO.password);
		doctor.setTypeOfDoctor(TypeOfDoctor.valueOf(doctorDTO.typeOfDoctor));
		doctor.setIsDeleted(false);
		doctor.setTelephone(doctorDTO.phoneNumber);
		doctor.setPharmacies(pharmacies);
		
		doctorRepository.save(doctor);
	}
	
	@Override
	public Collection<Doctor> findByPharmacyId(int id) {
		Pharmacy pharmacy = pharmacyRepository.getOne(id);
		Collection<Doctor> allDoctors = doctorRepository.findAll();
		Collection<Doctor> doctors = new ArrayList<Doctor>();
		for(Doctor d: allDoctors) {
			if(d.getPharmacies().contains(pharmacy) && d.isIsDeleted() == false) {
				doctors.add(d);
			}
		}
		return doctors;
	}
	
	@Override
	public Collection<ViewSearchedDoctorDTO> searchByNameAndSurname(String name, String surname, Collection<ViewSearchedDoctorDTO> doctorDTOs) {
		Collection<ViewSearchedDoctorDTO> searchResult = new ArrayList<>();
		
		if(name.equals("&")) name = "";
		if(surname.equals("&")) surname = "";
		
		for(ViewSearchedDoctorDTO doctor:doctorDTOs) {
			if(doctor.name.toLowerCase().contains(name.toLowerCase()) && doctor.surname.toLowerCase().contains(surname.toLowerCase())) {
				searchResult.add(doctor);
			}
		}
		return searchResult;
	}
	
	@Override
	public Collection<ViewSearchedDoctorDTO> filterByGradeAndType(String typeOfDoctor, int grade, Collection<ViewSearchedDoctorDTO> doctorDTOs) {
		Collection<ViewSearchedDoctorDTO> searchResult = new ArrayList<>();
		for(ViewSearchedDoctorDTO doctor:doctorDTOs) {
			if(doctor.typeOfDoctor == TypeOfDoctor.valueOf(typeOfDoctor) && doctor.grade <= grade && doctor.grade >= grade-1) {
				searchResult.add(doctor);
			}
		}
		return searchResult;
	}

	@Override
	public void deleteDoctor(int id) {
		Doctor doctor = doctorRepository.getOne(id);
		doctor.setIsDeleted(true);
		doctorRepository.save(doctor);
	}
	
	@Override
	public Collection<Doctor> getDoctorWithoutWorkingTime(int id){
		Pharmacy pharmacy = pharmacyRepository.getOne(id);
		Collection<Doctor> allDoctors = doctorRepository.findAll();
		Collection<Doctor> doctors = new ArrayList<Doctor>();
		for(Doctor d: allDoctors) {
			if(d.getPharmacies().contains(pharmacy) && d.getWorkingTimes().isEmpty()) {
				doctors.add(d);
			}
		}
		return doctors;
	}

	@Override
	public Collection<Map<String, Object>> report(int idPharmacy) {
		Collection<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Pharmacy pharmacy = pharmacyRepository.getOne(idPharmacy);
		for(Doctor d : findByPharmacyId(idPharmacy)) {
			Map<String, Object> item = new HashMap<>();
			item.put("pharmacyName", pharmacy.getName());
			item.put("pharmacyGrade", pharmacy.getAverageGrade());
			item.put("name", d.getName());
			item.put("surname", d.getSurname());
			item.put("typeOfDoctor", d.getTypeOfDoctor());
			item.put("averageGrade", d.getAverageGrade());
			result.add(item);
		}
		return result;
	}

}

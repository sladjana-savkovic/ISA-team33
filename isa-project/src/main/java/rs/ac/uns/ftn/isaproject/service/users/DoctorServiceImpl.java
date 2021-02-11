package rs.ac.uns.ftn.isaproject.service.users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddDermatologistDTO;
import rs.ac.uns.ftn.isaproject.dto.AddDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.dto.ViewSearchedDoctorDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.model.users.WorkingTime;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;

@Service
public class DoctorServiceImpl implements DoctorService {

	private DoctorRepository doctorRepository;
	private CityRepository cityRepository;
	private PharmacyRepository pharmacyRepository;
	private UserAccountService userAccountService;
	private VacationRequestService vacationRequestService;
	private WorkingTimeService workingTimeService;
	private AppointmentService appointmentService;
	
	@Autowired
	public DoctorServiceImpl(DoctorRepository doctorRepository, CityRepository cityRepository, PharmacyRepository pharmacyRepository, UserAccountService userAccountService,
			VacationRequestService vacationRequestService, WorkingTimeService workingTimeService, AppointmentService appointmentService) {
		this.doctorRepository = doctorRepository;
		this.cityRepository = cityRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.userAccountService = userAccountService;
		this.vacationRequestService = vacationRequestService;
		this.workingTimeService = workingTimeService;
		this.appointmentService = appointmentService;
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
	public void addPharmacist(AddDoctorDTO doctorDTO) {
		Doctor doctor = new Doctor();
		City city = cityRepository.getOne(doctorDTO.cityId);
		Set<Pharmacy> pharmacies = new HashSet<Pharmacy>();
		Pharmacy pharmacy = pharmacyRepository.getOne(doctorDTO.pharmacyId);
		pharmacies.add(pharmacy);
		
		doctor.setName(doctorDTO.name);
		doctor.setSurname(doctorDTO.surname);
		doctor.setDateOfBirth(doctorDTO.dateOfBirth);
		doctor.setAddress(doctorDTO.address);
		doctor.setCity(city);
		doctor.setTypeOfDoctor(TypeOfDoctor.Pharmacist);
		doctor.setIsDeleted(false);
		doctor.setTelephone(doctorDTO.phoneNumber);
		doctor.setPharmacies(pharmacies);
		
		userAccountService.save(doctorDTO.email, doctorDTO.password, "ROLE_PHARMACIST", false, doctor);
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
			int i=0;
			for(WorkingTime w: d.getWorkingTimes()) {
				if(w.getPharmacy().getId() == id || !w.getDoctor().getPharmacies().contains(pharmacy)) {
					i=1;
				}
			}
			if(i==0) {
				doctors.add(d);
			}		
		}
		return doctors;
	}
	
	@Override
	public void add(AddDermatologistDTO dermatologistDTO) {
		Doctor dermatologist = new Doctor();		
		City city = cityRepository.getOne(dermatologistDTO.cityId);
		dermatologist.setCity(city);				
		dermatologist.setName(dermatologistDTO.name);
		dermatologist.setSurname(dermatologistDTO.surname);
		dermatologist.setTelephone(dermatologistDTO.phoneNumber);	
		dermatologist.setAddress(dermatologistDTO.address);		
		dermatologist.setDateOfBirth(dermatologistDTO.dateOfBirth);
		dermatologist.setTypeOfDoctor(TypeOfDoctor.Dermatologist);		
		userAccountService.save(dermatologistDTO.email, dermatologistDTO.password, "ROLE_DERMATOLOGIST", true, dermatologist);	
	}

	@Override
	public void addDermatologistInPharmacy(int id, int idPharmacy) {
		Doctor doctor = doctorRepository.getOne(id);
		Set<Pharmacy> pharmacies = doctor.getPharmacies();
		Pharmacy pharmacy = pharmacyRepository.getOne(idPharmacy);
		pharmacies.add(pharmacy);
		doctor.setPharmacies(pharmacies);
		doctorRepository.save(doctor);
		
	}

	@Override
	public Collection<Doctor> findDoctorNotInPharmacy(int id) {
		Pharmacy pharmacy = pharmacyRepository.getOne(id);
		Collection<Doctor> allDoctors = doctorRepository.findAll();
		Collection<Doctor> doctors = new ArrayList<Doctor>();
		for(Doctor d: allDoctors) {
			if(!d.getPharmacies().contains(pharmacy) && d.isIsDeleted() == false) {
				doctors.add(d);
			}
		}
		return doctors;
	}
	
	@Override
	public Collection<Doctor> findAvailableDoctor(LocalDateTime date, Long idPharmacy) {
		Collection<Doctor> doctors= doctorRepository.findByTypeOfDoctor(TypeOfDoctor.Pharmacist);
		doctors = doctors.stream().filter(d-> !vacationRequestService.isDoctorOnVacation(d.getId(), d.getPharmacies().stream().findFirst().get().getId(), date.toLocalDate()))
		.filter(d->workingTimeService.checkIfDoctorWorkInPharmacy(d.getPharmacies().stream().findFirst().get().getId(),d.getId(), date.toLocalTime(), date.toLocalTime().plusMinutes(30)))
		.filter(d->appointmentService.isDoctorAvailableForChosenTime(d.getId(),date.toLocalDate(), date.toLocalTime(), date.toLocalTime().plusMinutes(30))).collect(Collectors.toList());
		
		if(idPharmacy != null) {
			doctors = doctors.stream().filter(d-> d.getPharmacies().stream().findFirst().get().getId() == idPharmacy).collect(Collectors.toList());
		}
		return doctors;
	}

	@Override
	public Collection<Doctor> findDoctorByType(int type) {
		if (type == 0)
			return doctorRepository.findByTypeOfDoctor(TypeOfDoctor.Dermatologist);
		return doctorRepository.findByTypeOfDoctor(TypeOfDoctor.Pharmacist);
	}

}

package rs.ac.uns.ftn.isaproject.service.pharmacy;

import static org.hamcrest.CoreMatchers.containsString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacySearchDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.service.users.DoctorService;

@Service
public class PharmacyServiceImpl implements PharmacyService {

	private PharmacyRepository pharmacyRepository;
	private CityRepository cityRepository;
	private DoctorService doctorService;
	
	@Autowired
	public PharmacyServiceImpl(PharmacyRepository pharmacyRepository, CityRepository cityRepository,DoctorService doctorService) {
		this.pharmacyRepository = pharmacyRepository;
		this.cityRepository = cityRepository;
		this.doctorService = doctorService;
	}

	@Override
	public Pharmacy findOneById(int id) {
		
		return pharmacyRepository.findOneById(id);
	}

	@Override
	public void add(PharmacyDTO pharmacyDTO) {
		Pharmacy pharmacy = new Pharmacy();
		
		City city = cityRepository.getOne(pharmacyDTO.cityId);
		pharmacy.setCity(city);				
		pharmacy.setName(pharmacyDTO.name);
		pharmacy.setAddress(pharmacyDTO.address);
		pharmacy.setPharmacistPrice(pharmacyDTO.pharmacistPrice);
		pharmacy.setLatitude(0);
		pharmacy.setLongitude(0);
		pharmacy.setAverageGrade(0);
		
		pharmacyRepository.save(pharmacy);			
	}
	
	
	public Collection<Pharmacy> findAll() {
		return pharmacyRepository.findAll();
	}

	@Override
	public Collection<Pharmacy> findAvailablePharmacy(LocalDateTime date) {
		Collection<Doctor> doctors = doctorService.findAvailableDoctor(date, null);
		HashSet<Pharmacy> pharmacies = new HashSet<Pharmacy>();
		for(Doctor d: doctors) {
			for (Pharmacy p:d.getPharmacies())
				pharmacies.add(p);
		}
		return pharmacies;
	}
	
	@Override
	public Collection<PharmacyDTO> searchByNameAndCityAndAddressAndGradeAndPrice(PharmacySearchDTO dto) {			
		Collection<PharmacyDTO> searchResult = new ArrayList<>();			
		for(PharmacyDTO pharmacy : dto.pharmacyDTOs) {
			
			if(!dto.name.equals("") && !pharmacy.name.toLowerCase().contains(dto.name.toLowerCase())) {
				continue;
			}
			if(!dto.cityName.equals("") && !pharmacy.cityName.toLowerCase().contains(dto.cityName.toLowerCase())) {
				continue;
			}
			if(!dto.address.equals("") && !pharmacy.address.toLowerCase().contains(dto.address.toLowerCase())) {
				continue;
			}
			if(dto.gradeMin!=0.0 && pharmacy.averageGrade < dto.gradeMin) {
				continue;
			}
			if(dto.gradeMax!=0.0 && pharmacy.averageGrade > dto.gradeMax) {
				continue;
			}
			if(dto.priceMin!=0.0 && pharmacy.pharmacistPrice > dto.priceMin) {
				continue;
			}
			if(dto.priceMax!=0.0 && pharmacy.pharmacistPrice > dto.priceMax) {
				continue;
			}
			searchResult.add(pharmacy);
			
		} 
		
		return searchResult;
	}
	
}

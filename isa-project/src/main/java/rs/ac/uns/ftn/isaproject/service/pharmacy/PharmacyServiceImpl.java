package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.PharmacyDTO;
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
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	public PharmacyServiceImpl(PharmacyRepository pharmacyRepository, CityRepository cityRepository) {
		this.pharmacyRepository = pharmacyRepository;
		this.cityRepository = cityRepository;
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
			pharmacies.add(d.getPharmacies().stream().findFirst().get());
		}
		return pharmacies;
	}

}

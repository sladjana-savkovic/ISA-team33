package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.PharmacyAdministratorDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.PharmacyAdministrator;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.users.PharmacyAdministratorRepository;

@Service
public class PharmacyAdministratorServiceImpl implements PharmacyAdministratorService {

	private PharmacyAdministratorRepository administratorRepository;
	private CityRepository cityRepository;
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	public PharmacyAdministratorServiceImpl(PharmacyAdministratorRepository administratorRepository, CityRepository cityRepository, PharmacyRepository pharmacyRepository) {
		this.administratorRepository = administratorRepository;
		this.cityRepository = cityRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public PharmacyAdministrator getOne(int id) {
		return administratorRepository.getOne(id);
	}

	@Override
	public void updateInfo(PharmacyAdministratorDTO pharmacyAdministratorDTO) {
		PharmacyAdministrator pharmacyAdministrator = administratorRepository.getOne(pharmacyAdministratorDTO.id);
		City city = cityRepository.getOne(pharmacyAdministratorDTO.cityId);
		Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyAdministratorDTO.pharmacyId);
		
		pharmacyAdministrator.setName(pharmacyAdministratorDTO.name);
		pharmacyAdministrator.setSurname(pharmacyAdministratorDTO.surname);
		pharmacyAdministrator.setEmail(pharmacyAdministratorDTO.email);
		pharmacyAdministrator.setTelephone(pharmacyAdministratorDTO.telephone);
		pharmacyAdministrator.setAddress(pharmacyAdministratorDTO.address);
		pharmacyAdministrator.setCity(city);
		pharmacyAdministrator.setPharmacy(pharmacy);
		pharmacyAdministrator.setPassword(pharmacyAdministratorDTO.password);
		
		administratorRepository.save(pharmacyAdministrator);
		
	}

	@Override
	public void updatePassword(int id, String password) {
		PharmacyAdministrator pharmacyAdministrator = administratorRepository.getOne(id);
		pharmacyAdministrator.setPassword(password);
		administratorRepository.save(pharmacyAdministrator);
		
	}
}

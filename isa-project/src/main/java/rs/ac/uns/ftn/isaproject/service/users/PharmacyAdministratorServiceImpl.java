package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddPharmacyAdministratorDTO;
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
	private UserAccountService userAccountService;
	
	@Autowired
	public PharmacyAdministratorServiceImpl(PharmacyAdministratorRepository administratorRepository, CityRepository cityRepository, PharmacyRepository pharmacyRepository, UserAccountService userAccountService) {
		this.administratorRepository = administratorRepository;
		this.cityRepository = cityRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.userAccountService = userAccountService;
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
		pharmacyAdministrator.setTelephone(pharmacyAdministratorDTO.telephone);
		pharmacyAdministrator.setAddress(pharmacyAdministratorDTO.address);
		pharmacyAdministrator.setCity(city);
		pharmacyAdministrator.setPharmacy(pharmacy);
		
		administratorRepository.save(pharmacyAdministrator);
		
	}
	
	@Override
	public void add(AddPharmacyAdministratorDTO pharmacyAdministratorDTO) throws MailException, InterruptedException, Exception {
		PharmacyAdministrator pharmacyAdministrator = new PharmacyAdministrator();		
		City city = cityRepository.getOne(pharmacyAdministratorDTO.cityId);
		pharmacyAdministrator.setCity(city);				
		pharmacyAdministrator.setName(pharmacyAdministratorDTO.name);
		pharmacyAdministrator.setSurname(pharmacyAdministratorDTO.surname);
		pharmacyAdministrator.setTelephone(pharmacyAdministratorDTO.telephone);	
		pharmacyAdministrator.setAddress(pharmacyAdministratorDTO.address);		
		pharmacyAdministrator.setDateOfBirth(pharmacyAdministratorDTO.dateOfBirth);
		Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyAdministratorDTO.pharmacyId);
		pharmacyAdministrator.setPharmacy(pharmacy);
		userAccountService.save(pharmacyAdministratorDTO.email, pharmacyAdministratorDTO.password, "ROLE_PHARMACYADMIN", true, pharmacyAdministrator);					
	}
}

package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddSystemAdministratorDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.users.SystemAdministrator;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;
import rs.ac.uns.ftn.isaproject.repository.users.SystemAdministratorRepository;

@Service
public class SystemAdministratorServiceImpl implements SystemAdministratorService {
	
	private UserAccountService userAccountService;
	private CityRepository cityRepository;
	private SystemAdministratorRepository systemAdministratorRepository;
	
	@Autowired
	public SystemAdministratorServiceImpl(CityRepository cityRepository, UserAccountService userAccountService, SystemAdministratorRepository systemAdministratorRepository) {
		this.systemAdministratorRepository = systemAdministratorRepository;
		this.cityRepository = cityRepository;
		this.userAccountService = userAccountService;
	}
	
		
	@Override
	public SystemAdministrator getOne(int id) {
		return systemAdministratorRepository.getOne(id);
	}	
	
	@Override
	public void add(AddSystemAdministratorDTO systemAdministratorDTO) throws MailException, InterruptedException, Exception {
		SystemAdministrator systemAdministrator = new SystemAdministrator();		
		City city = cityRepository.getOne(systemAdministratorDTO.cityId);
		systemAdministrator.setCity(city);				
		systemAdministrator.setName(systemAdministratorDTO.name);
		systemAdministrator.setSurname(systemAdministratorDTO.surname);
		systemAdministrator.setTelephone(systemAdministratorDTO.telephone);	
		systemAdministrator.setAddress(systemAdministratorDTO.address);		
		systemAdministrator.setDateOfBirth(systemAdministratorDTO.dateOfBirth);
		userAccountService.save(systemAdministratorDTO.email, systemAdministratorDTO.password, "ROLE_SYSTEMADMIN", true, systemAdministrator);					
	}
	
	
}

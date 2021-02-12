package rs.ac.uns.ftn.isaproject.service.users;

import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddSupplierDTO;
import rs.ac.uns.ftn.isaproject.dto.SupplierDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;
import rs.ac.uns.ftn.isaproject.repository.users.SupplierRepository;
import rs.ac.uns.ftn.isaproject.repository.users.UserAccountRepository;

@Service
public class SupplierServiceImpl implements SupplierService {

	private CityRepository cityRepository;
	private SupplierRepository supplierRepository;
	private UserAccountRepository userAccountRepository;
	private UserAccountService userAccountService;
	
	@Autowired
	public SupplierServiceImpl(SupplierRepository supplierRepository, CityRepository cityRepository, UserAccountRepository userAccountRepository, UserAccountService userAccountService) {
		this.supplierRepository = supplierRepository;
		this.cityRepository = cityRepository;
		this.userAccountRepository = userAccountRepository;
		this.userAccountService = userAccountService;
	}
	
	@Override
	public Supplier getOne(int id) {
		return supplierRepository.getOne(id);
	}

	@Override
	public void updateInfo(SupplierDTO supplierDTO) {
		Supplier supplier = supplierRepository.getOne(supplierDTO.id);
		City city = cityRepository.getOne(supplierDTO.cityId);
		
		supplier.setName(supplierDTO.name);
		supplier.setSurname(supplierDTO.surname);
		supplier.setTelephone(supplierDTO.telephone);		
		supplier.setAddress(supplierDTO.address);
		supplier.setCity(city);
		
		supplierRepository.save(supplier);		
	}

	@Override
	public void add(AddSupplierDTO supplierDTO) throws MailException, InterruptedException, Exception {
		Supplier supplier = new Supplier();		
		City city = cityRepository.getOne(supplierDTO.cityId);
		supplier.setCity(city);				
		supplier.setName(supplierDTO.name);
		supplier.setSurname(supplierDTO.surname);
		supplier.setTelephone(supplierDTO.telephone);	
		supplier.setAddress(supplierDTO.address);		
		supplier.setDateOfBirth(supplierDTO.dateOfBirth);
		userAccountService.save(supplierDTO.email, supplierDTO.password, "ROLE_SUPPLIER", true, supplier);			
	}

	
	@Override
	public UserAccount getOne(long id) throws AccessDeniedException {
		return userAccountService.findById(id);
	}
	
}

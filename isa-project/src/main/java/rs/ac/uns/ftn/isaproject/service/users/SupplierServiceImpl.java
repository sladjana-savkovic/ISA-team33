package rs.ac.uns.ftn.isaproject.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Autowired
	public SupplierServiceImpl(SupplierRepository supplierRepository, CityRepository cityRepository, UserAccountRepository userAccountRepository) {
		this.supplierRepository = supplierRepository;
		this.cityRepository = cityRepository;
		this.userAccountRepository = userAccountRepository;
	}
	
	@Override
	public Supplier getOne(int id) {
		return supplierRepository.getOne(id);
	}
	
	@Override
	public void updatePassword(int id, String password) {
		Supplier supplier = supplierRepository.getOne(id);
		supplier.setPassword(password);
		supplierRepository.save(supplier);
	}

	@Override
	public void updateInfo(SupplierDTO supplierDTO) {
		Supplier supplier = supplierRepository.getOne(supplierDTO.id);
		City city = cityRepository.getOne(supplierDTO.cityId);
		
		supplier.setName(supplierDTO.name);
		supplier.setSurname(supplierDTO.surname);
		//supplier.setDateOfBirth(supplierDTO.dateOfBirth);
		supplier.setEmail(supplierDTO.email);
		supplier.setPassword(supplierDTO.password);
		supplier.setTelephone(supplierDTO.telephone);		
		supplier.setAddress(supplierDTO.address);
		supplier.setCity(city);
		
		supplierRepository.save(supplier);		
	}

	@Override
	public void add(SupplierDTO supplierDTO) {
		// TODO Auto-generated method stub		
	}

	@Override
	public UserAccount getOne(long id) {
		return userAccountRepository.getOne(id);
	}
	
}

package rs.ac.uns.ftn.isaproject.mapper;

import rs.ac.uns.ftn.isaproject.dto.SupplierDTO;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

public class SupplierMapper {

	public static SupplierDTO toSupplierDTO(Supplier supplier) {
		
		return new SupplierDTO(
				 supplier.getId(), 
				 supplier.getName(), 
				 supplier.getSurname(), 
				 supplier.getDateOfBirth(), 
				 supplier.getEmail(), 
				 supplier.getPassword(),
				 supplier.getCity().getId(),
				 supplier.getCity().getName(),
				 supplier.getCity().getCountry().getId(), 
				 supplier.getCity().getCountry().getName(), 
				 supplier.getAddress(),
				 supplier.getTelephone());
	}
	
	
	public static SupplierDTO toSupplierAccountDTO(UserAccount supplierAccount) {
		
		return new SupplierDTO(
				 supplierAccount.getUser().getId(), 
				 supplierAccount.getUser().getName(), 
				 supplierAccount.getUser().getSurname(), 
				 supplierAccount.getUser().getDateOfBirth(), 
				 supplierAccount.getUsername(), 
				 supplierAccount.getPassword(),
				 supplierAccount.getUser().getCity().getCountry().getId(), 
				 supplierAccount.getUser().getCity().getCountry().getName(),
				 supplierAccount.getUser().getCity().getId(),
				 supplierAccount.getUser().getCity().getName(),				 				  
				 supplierAccount.getUser().getAddress(),
				 supplierAccount.getUser().getTelephone());
	}

}

package rs.ac.uns.ftn.isaproject.mapper;

import rs.ac.uns.ftn.isaproject.dto.SupplierDTO;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

public class SupplierMapper {
	
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

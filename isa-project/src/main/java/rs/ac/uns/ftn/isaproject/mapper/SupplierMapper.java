package rs.ac.uns.ftn.isaproject.mapper;

import rs.ac.uns.ftn.isaproject.dto.SupplierDTO;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;

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
	
}

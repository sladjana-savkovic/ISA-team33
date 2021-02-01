package rs.ac.uns.ftn.isaproject.service.users;

import rs.ac.uns.ftn.isaproject.dto.SupplierDTO;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;

public interface SupplierService {
	
	Supplier getOne(int id);
	void updateInfo(SupplierDTO supplierDTO);
	void updatePassword(int id, String password);
	void add(SupplierDTO supplierDTO);

}

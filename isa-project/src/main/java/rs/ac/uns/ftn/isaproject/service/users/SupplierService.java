package rs.ac.uns.ftn.isaproject.service.users;

import rs.ac.uns.ftn.isaproject.dto.SupplierDTO;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

public interface SupplierService {
	
	Supplier getOne(int id);
	UserAccount getOne(long id);
	void updateInfo(SupplierDTO supplierDTO);
	void updatePassword(int id, String password);
	void add(SupplierDTO supplierDTO);

}

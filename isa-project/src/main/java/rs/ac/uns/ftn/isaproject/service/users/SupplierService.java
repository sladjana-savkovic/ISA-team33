package rs.ac.uns.ftn.isaproject.service.users;

import java.nio.file.AccessDeniedException;

import org.springframework.mail.MailException;

import rs.ac.uns.ftn.isaproject.dto.AddSupplierDTO;
import rs.ac.uns.ftn.isaproject.dto.SupplierDTO;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

public interface SupplierService {
	
	Supplier getOne(int id);
	UserAccount getOne(long id) throws AccessDeniedException;
	void updateInfo(SupplierDTO supplierDTO);
	void updatePassword(int id, String password);
	void add(AddSupplierDTO supplierDTO) throws MailException, InterruptedException;

}

package rs.ac.uns.ftn.isaproject.controller.users;

import java.nio.file.AccessDeniedException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.AddSupplierDTO;
import rs.ac.uns.ftn.isaproject.dto.SupplierDTO;
import rs.ac.uns.ftn.isaproject.mapper.SupplierMapper;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.users.SupplierService;


@RestController
@RequestMapping(value = "api/supplier")
public class SupplierController {

	private SupplierService supplierService;
	
	@Autowired
	public SupplierController(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	
	
	@GetMapping("/account")
	@PreAuthorize("hasRole('ROLE_SUPPLIER')")
	public ResponseEntity<?> findOneById() throws AccessDeniedException {
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			long supplierAccountId = u.getId();
			SupplierDTO supplierDTO = SupplierMapper.toSupplierAccountDTO(supplierService.getOne(supplierAccountId));
			return new ResponseEntity<SupplierDTO>(supplierDTO, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>("The requested supplier doesn't exist in the database.", HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity<>("The requested supplier doesn't exist in the database.", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_SUPPLIER')")
	public ResponseEntity<Void> updateInfo(@RequestBody SupplierDTO supplierDTO) {		
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			supplierDTO.id = u.getUser().getId();
			supplierService.updateInfo(supplierDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}		
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
	public ResponseEntity<?> add(@RequestBody AddSupplierDTO supplierDTO){
		try {
			supplierService.add(supplierDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
}

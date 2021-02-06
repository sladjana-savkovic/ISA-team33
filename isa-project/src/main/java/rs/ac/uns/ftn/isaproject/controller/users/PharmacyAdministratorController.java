package rs.ac.uns.ftn.isaproject.controller.users;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.AddPharmacyAdministratorDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyAdministratorDTO;
import rs.ac.uns.ftn.isaproject.mapper.PharmacyAdministratorMapper;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.users.PharmacyAdministratorService;
import rs.ac.uns.ftn.isaproject.service.users.UserAccountService;

@RestController
@RequestMapping(value = "api/pharmacy-admin")
public class PharmacyAdministratorController {

	private PharmacyAdministratorService administratorService;
	private UserAccountService userAccountService;
	
	@Autowired
	public PharmacyAdministratorController(PharmacyAdministratorService administratorService, UserAccountService userAccountService) {
		this.administratorService = administratorService;
		this.userAccountService = userAccountService;
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<PharmacyAdministratorDTO> findOneById(@PathVariable int id) {
		try {
			PharmacyAdministratorDTO pharmacyAdministratorDTO = PharmacyAdministratorMapper.toPharmacyAdministratorDTO(administratorService.getOne(id));
			UserAccount account = userAccountService.findByUserId(id);
			pharmacyAdministratorDTO.setEmail(account.getUsername());
			pharmacyAdministratorDTO.setPassword(account.getPassword());
			return new ResponseEntity<PharmacyAdministratorDTO>(pharmacyAdministratorDTO, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Void> updateInfo(@RequestBody PharmacyAdministratorDTO pharmacyAdministratorDTO){
		administratorService.updateInfo(pharmacyAdministratorDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{id}/password/{value}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Void> updatePassword(@PathVariable int id, @PathVariable String value){
		try{
		UserAccount account = userAccountService.findByUserId(id);
		userAccountService.updatePassword(account.getId(), account.getPassword(), value);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody AddPharmacyAdministratorDTO pharmacyAdministratorDTO){
		try {
			administratorService.add(pharmacyAdministratorDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}

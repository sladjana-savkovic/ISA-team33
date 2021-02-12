package rs.ac.uns.ftn.isaproject.controller.users;

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
	
	@GetMapping()
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> findOneById() {
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PharmacyAdministratorDTO pharmacyAdministratorDTO = PharmacyAdministratorMapper.toPharmacyAdministratorDTO(administratorService.getOne(u.getUser().getId()));
			UserAccount account = userAccountService.findByUserId(u.getUser().getId());
			pharmacyAdministratorDTO.setEmail(account.getUsername());
			return new ResponseEntity<PharmacyAdministratorDTO>(pharmacyAdministratorDTO, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting pharmacy administrator.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> updateInfo(@RequestBody PharmacyAdministratorDTO pharmacyAdministratorDTO){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pharmacyAdministratorDTO.id = u.getUser().getId();
			administratorService.updateInfo(pharmacyAdministratorDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while updating pharmacy administrator's information.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
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

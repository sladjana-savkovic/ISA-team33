package rs.ac.uns.ftn.isaproject.controller.users;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddPatientDTO;
import rs.ac.uns.ftn.isaproject.dto.PatientDTO;
import rs.ac.uns.ftn.isaproject.mapper.PatientMapper;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.users.PatientService;
import rs.ac.uns.ftn.isaproject.service.users.UserAccountService;

@RestController
@RequestMapping(value = "api/patient")
public class PatientController {

	private PatientService patientService;
	private UserAccountService userAccountService;
	
	@Autowired
	public PatientController(PatientService patientService, UserAccountService userAccountService) {
		this.patientService = patientService;
		this.userAccountService = userAccountService;
	}
	
	@GetMapping("")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<PatientDTO> findOneById() {
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			PatientDTO patientDTO = PatientMapper.toPatientDTO(patientService.getOne(u.getUser().getId()));
			patientDTO.setEmail(userAccountService.findByUserId(u.getUser().getId()).getUsername());
			patientDTO.setPassword(userAccountService.findByUserId(u.getUser().getId()).getPassword());
			return new ResponseEntity<PatientDTO>(patientDTO, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<Void> updateInfo(@RequestBody PatientDTO patientDTO){
		patientService.updateInfo(patientDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{id}/increase-penalty")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Void> increasePenalty(@PathVariable int id){
		try {
			patientService.increasePenalty(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody AddPatientDTO addPatientDTO){
		try {
			patientService.add(addPatientDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/allergy/{drugId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST','PATIENT', 'PHARMACIST')")
	public ResponseEntity<Boolean> checkAllergyOnDrug(@PathVariable int id, @PathVariable int drugId){
		try {
			return new ResponseEntity<Boolean>(patientService.checkAllergyOnDrug(id, drugId), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/doctor/examined")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> findExaminedPatientsByDoctor(){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<PatientDTO> patientDTOs = PatientMapper.toPatientDTOs(patientService.findExaminedPatientsByDoctorId(u.getUser().getId()));
			return new ResponseEntity<Collection<PatientDTO>>(patientDTOs, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/doctor/unexamined")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> findUnexaminedPatientsByDoctor(){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<PatientDTO> patientDTOs = PatientMapper.toPatientDTOs(patientService.findUnexaminedPatientsByDoctorId(u.getUser().getId()));
			return new ResponseEntity<Collection<PatientDTO>>(patientDTOs, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/search/{name}/{surname}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Collection<PatientDTO>> searchByNameAndSurname(@PathVariable String name,@PathVariable String surname,@RequestBody ArrayList<PatientDTO> patientDTOs){
		Collection<PatientDTO> searchResult = patientService.searchByNameAndSurname(name, surname, patientDTOs);
		return new ResponseEntity<Collection<PatientDTO>>(searchResult, HttpStatus.OK);
	}
	
}

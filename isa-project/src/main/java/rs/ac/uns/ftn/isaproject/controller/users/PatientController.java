package rs.ac.uns.ftn.isaproject.controller.users;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('PATIENT')")
	public ResponseEntity<PatientDTO> findOneById(@PathVariable int id) {
		try {
			PatientDTO patientDTO = PatientMapper.toPatientDTO(patientService.getOne(id));
			patientDTO.setEmail(userAccountService.findByUserId(id).getUsername());
			patientDTO.setPassword(userAccountService.findByUserId(id).getPassword());
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
	public ResponseEntity<?> add(@RequestBody AddPatientDTO addPatientDTO){
		try {
			patientService.add(addPatientDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
	
	@GetMapping("/doctor/{doctorId}/examined")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> findExaminedPatientsByDoctor(@PathVariable int doctorId){
		try {
			Collection<PatientDTO> patientDTOs = PatientMapper.toPatientDTOs(patientService.findExaminedPatientsByDoctorId(doctorId));
			return new ResponseEntity<Collection<PatientDTO>>(patientDTOs, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/doctor/{doctorId}/unexamined")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> findUnexaminedPatientsByDoctor(@PathVariable int doctorId){
		try {
			Collection<PatientDTO> patientDTOs = PatientMapper.toPatientDTOs(patientService.findUnexaminedPatientsByDoctorId(doctorId));
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

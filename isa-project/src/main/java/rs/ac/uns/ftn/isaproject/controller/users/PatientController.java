package rs.ac.uns.ftn.isaproject.controller.users;

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

@RestController
@RequestMapping(value = "api/patient")
public class PatientController {

	private PatientService patientService;
	
	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}
	@GetMapping("/{id}")
	public ResponseEntity<PatientDTO> findOneById(@PathVariable int id) {
		try {
			PatientDTO patientDTO = PatientMapper.toPatientDTO(patientService.getOne(id));
			return new ResponseEntity<PatientDTO>(patientDTO, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(consumes = "application/json")
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
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Boolean> checkAllergyOnDrug(@PathVariable int id, @PathVariable int drugId){
		try {
			return new ResponseEntity<Boolean>(patientService.checkAllergyOnDrug(id, drugId), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}

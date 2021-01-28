package rs.ac.uns.ftn.isaproject.controller.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.service.users.PatientService;

@RestController
@RequestMapping(value = "api/patient")
public class PatientController {

	private PatientService patientService;
	
	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}
	
	/*@GetMapping("/examined/{id}")
	public ResponseEntity<Collection<ExaminedPatientDTO>> findAllExaminedByDoctor(@PathVariable int id){
		Collection<ExaminedPatientDTO> examinedPatientDTOs = 
				ExaminedPatientMapper.toExaminedPatientDTOs(patientService.findAllExaminedByDoctor(id));
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(examinedPatientDTOs,HttpStatus.OK);
	}*/
}

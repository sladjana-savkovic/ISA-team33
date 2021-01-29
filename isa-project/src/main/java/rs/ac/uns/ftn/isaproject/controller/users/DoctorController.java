package rs.ac.uns.ftn.isaproject.controller.users;

import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorPharmacyDTO;
import rs.ac.uns.ftn.isaproject.mapper.DoctorMapper;
import rs.ac.uns.ftn.isaproject.service.users.DoctorService;

@RestController
@RequestMapping(value = "api/doctor")
public class DoctorController {

	private DoctorService doctorService;
	
	@Autowired
	public DoctorController(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DoctorDTO> findOneById(@PathVariable int id) {
		try {
			DoctorDTO doctorDTO = DoctorMapper.toDoctorDTO(doctorService.getOne(id));
			return new ResponseEntity<DoctorDTO>(doctorDTO, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<Void> updateInfo(@RequestBody DoctorDTO doctorDTO){
		doctorService.updateInfo(doctorDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{id}/password/{value}")
	public ResponseEntity<Void> updatePassword(@PathVariable int id, @PathVariable String value){
		doctorService.updatePassword(id,value);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}/pharmacies")
	public ResponseEntity<Collection<DoctorPharmacyDTO>> doctorPharmacies(@PathVariable int id){
		try {
			Collection<DoctorPharmacyDTO> doctorPharmacyDTOs = DoctorMapper.toDoctorPharmacyDTOs(doctorService.getOne(id));
			return new ResponseEntity<Collection<DoctorPharmacyDTO>>(doctorPharmacyDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}

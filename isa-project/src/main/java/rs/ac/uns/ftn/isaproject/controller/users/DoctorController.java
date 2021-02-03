package rs.ac.uns.ftn.isaproject.controller.users;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.AddDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.ViewSearchedDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorPharmacyDTO;
import rs.ac.uns.ftn.isaproject.mapper.DoctorMapper;
import rs.ac.uns.ftn.isaproject.mapper.ViewSearchedDoctorMapper;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;
import rs.ac.uns.ftn.isaproject.service.users.DoctorService;

@RestController
@RequestMapping(value = "api/doctor")
public class DoctorController {

	private DoctorService doctorService;
	private AppointmentService appointmentService;
	
	@Autowired
	public DoctorController(DoctorService doctorService, AppointmentService appointmentService) {
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneById(@PathVariable int id) {
		try {
			DoctorDTO doctorDTO = DoctorMapper.toDoctorDTO(doctorService.getOne(id));
			return new ResponseEntity<DoctorDTO>(doctorDTO, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("The requested doctor doesn't exist in the database.", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<?> updateInfo(@RequestBody DoctorDTO doctorDTO){
		try {
			doctorService.updateInfo(doctorDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while updating doctor's information.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}/password/{value}")
	public ResponseEntity<?> updatePassword(@PathVariable int id, @PathVariable String value){
		try {
			doctorService.updatePassword(id,value);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while updating doctor's password.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/pharmacies")
	public ResponseEntity<?> doctorPharmacies(@PathVariable int id){
		try {
			Collection<DoctorPharmacyDTO> doctorPharmacyDTOs = DoctorMapper.toDoctorPharmacyDTOs(doctorService.getOne(id));
			return new ResponseEntity<Collection<DoctorPharmacyDTO>>(doctorPharmacyDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>("The requested doctor doesn't exist in the database", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/pharmacy")
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> findByPharmacyId(@PathVariable int id) {
		try {
			Collection<ViewSearchedDoctorDTO> doctorDTOs = ViewSearchedDoctorMapper.toViewSearchedDoctorDTODrugDTOs(doctorService.findByPharmacyId(id));
			return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(doctorDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/search/{name}/{surname}")
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> searchByNameAndSurname(@PathVariable String name,@PathVariable String surname,@RequestBody ArrayList<ViewSearchedDoctorDTO> doctorDTOs){
		Collection<ViewSearchedDoctorDTO> searchResult = doctorService.searchByNameAndSurname(name, surname, doctorDTOs);
		return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(searchResult, HttpStatus.OK);
	}
	
	@PostMapping("/filter/{type}/{grade}")
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> filterByGradeAndType(@PathVariable String type,@PathVariable int grade,@RequestBody ArrayList<ViewSearchedDoctorDTO> doctorDTOs){
		Collection<ViewSearchedDoctorDTO> searchResult = doctorService.filterByGradeAndType(type, grade, doctorDTOs);
		return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(searchResult, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody AddDoctorDTO doctorDTO){
		try {
			doctorService.add(doctorDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id_doctor}/pharmacy/{id_pharmacy}/delete")
	public ResponseEntity<Void> deleteDoctor(@PathVariable int id_doctor, @PathVariable int id_pharmacy){
		if(appointmentService.getDoctorScheduledAppointmentsInPharamacy(id_doctor, id_pharmacy).isEmpty()) {
			doctorService.deleteDoctor(id_doctor);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/pharmacy/without/working-time")
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> findDoctorWithoutWorkingTime(@PathVariable int id) {
		try {
			Collection<ViewSearchedDoctorDTO> doctorDTOs = ViewSearchedDoctorMapper.toViewSearchedDoctorDTODrugDTOs(doctorService.getDoctorWithoutWorkingTime(id));
			return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(doctorDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
}

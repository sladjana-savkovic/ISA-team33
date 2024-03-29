package rs.ac.uns.ftn.isaproject.controller.users;

import java.util.Collection;

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
import rs.ac.uns.ftn.isaproject.dto.AddVacationRequestDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.ViewVacationRequestDTO;
import rs.ac.uns.ftn.isaproject.mapper.DoctorMapper;
import rs.ac.uns.ftn.isaproject.mapper.VacationRequestMapper;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.users.VacationRequestService;

@RestController
@RequestMapping(value = "api/vacation")
public class VacationRequestController {

	private VacationRequestService vacationService;
	
	@Autowired
	public VacationRequestController(VacationRequestService vacationService) {
		this.vacationService = vacationService;
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> add(@RequestBody AddVacationRequestDTO vacationRequestDTO){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vacationRequestDTO.doctorId = u.getUser().getId();
			vacationService.add(vacationRequestDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating a vacation request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_PHARMACYADMIN', 'ROLE_SYSTEMADMIN')")
	public ResponseEntity<?> acceptRequest(@PathVariable int id){
		try {
			vacationService.acceptRequest(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while acception doctor's vacation request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}/reason/{value}")
	@PreAuthorize("hasAnyRole('ROLE_PHARMACYADMIN', 'ROLE_SYSTEMADMIN')")
	public ResponseEntity<?> rejectRequest(@PathVariable int id, @PathVariable String value){
		try {
			vacationService.rejectRequest(id, value);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while rejection doctor's vacation request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/pharmacy/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> findCreatedByPharmacyId(@PathVariable int id){
		try {
			Collection<ViewVacationRequestDTO> vacationRequestDTOs = VacationRequestMapper.toViewVacationRequestDTOs(vacationService.findCreatedByPharmacyId(id));
			return new ResponseEntity<Collection<ViewVacationRequestDTO>>(vacationRequestDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting doctor's vacation request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/doctor")
	@PreAuthorize("hasAnyRole('ROLE_PHARMACYADMIN', 'ROLE_SYSTEMADMIN')")
	public ResponseEntity<?> findDoctorById(@PathVariable int id){
		try {
			DoctorDTO doctorDTO = DoctorMapper.toDoctorDTO(vacationService.findDoctorById(id));
			return new ResponseEntity<DoctorDTO>(doctorDTO, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while gettion doctor with vacation request.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
	public ResponseEntity<?> findAllCreated(){
		try {
			Collection<ViewVacationRequestDTO> vacationRequestDTOs = VacationRequestMapper.toViewVacationRequestDTOs(vacationService.findAllCreatedRequest());
			return new ResponseEntity<Collection<ViewVacationRequestDTO>>(vacationRequestDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting vacation requests.", HttpStatus.BAD_REQUEST);
		}
	}
}

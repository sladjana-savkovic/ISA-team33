package rs.ac.uns.ftn.isaproject.controller.pharmacy;

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
import rs.ac.uns.ftn.isaproject.dto.DrugReservationDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugReservationMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugReservationService;

@RestController
@RequestMapping(value = "api/drug-reservation")
public class DrugReservationController {

	private DrugReservationService drugReservationService;
	
	@Autowired
	public DrugReservationController(DrugReservationService drugReservationService) {
		this.drugReservationService = drugReservationService;
	}
	
	@GetMapping("/{id}/doctor")
	@PreAuthorize("hasAnyRole('PHARMACIST')")
	public ResponseEntity<?> searchOne(@PathVariable int id){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DrugReservation drugReservation = drugReservationService.searchOne(id,u.getUser().getId());
			return new ResponseEntity<DrugReservationDTO>(DrugReservationMapper.toDrugReservationDTO(drugReservation),HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>("The reservation number is incorrect.", HttpStatus.NOT_FOUND);
		}	
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('PHARMACIST')")
	public ResponseEntity<?> confirmReservation(@PathVariable int id){
		try {
			DrugReservation drugReservation =  drugReservationService.confirmReservation(id);
			return new ResponseEntity<Integer>(drugReservation.getPatient().getId(), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while confirming a reservation.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> createReservation(@RequestBody DrugReservationDTO drugReservation) {
		try {
			DrugReservation DrugReservation = drugReservationService.createReservation(drugReservation);
			return new ResponseEntity<DrugReservationDTO>(DrugReservationMapper.toDrugReservationDTO(DrugReservation),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating a reservation.", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}/cancel")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<?> cancelReservation(@PathVariable int id) {
		try {
			drugReservationService.cancelReservation(id);
			return new ResponseEntity<Integer>(id, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred while cancelling a reservation.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/patient")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Collection<DrugReservationDTO>> getUnfinishedReservationsByPatient(){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<DrugReservationDTO> drugDTOs = DrugReservationMapper.toDrugReservationDTOs(drugReservationService.findUnfinishedReservationsByPatient(u.getUser().getId()));
			return new ResponseEntity<Collection<DrugReservationDTO>>(drugDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/patient/finished")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Collection<DrugReservationDTO>> getFinishedReservationsByPatient(){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<DrugReservationDTO> drugDTOs = DrugReservationMapper.toDrugReservationDTOs(drugReservationService.findFinishedReservationsByPatient(u.getUser().getId()));
			return new ResponseEntity<Collection<DrugReservationDTO>>(drugDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

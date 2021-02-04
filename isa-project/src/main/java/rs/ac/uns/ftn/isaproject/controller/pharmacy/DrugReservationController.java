package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.DrugReservationDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugReservationMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugReservationService;

@RestController
@RequestMapping(value = "api/drug-reservation")
public class DrugReservationController {

	private DrugReservationService drugReservationService;
	
	@Autowired
	public DrugReservationController(DrugReservationService drugReservationService) {
		this.drugReservationService = drugReservationService;
	}
	
	@GetMapping("/{id}/doctor/{doctorId}")
	public ResponseEntity<?> searchOne(@PathVariable int id,@PathVariable int doctorId){
		try {
			DrugReservation drugReservation = drugReservationService.searchOne(id,doctorId);
			return new ResponseEntity<DrugReservationDTO>(DrugReservationMapper.toDrugReservationDTO(drugReservation),HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>("The reservation number is incorrect.", HttpStatus.NOT_FOUND);
		}	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> confirmReservation(@PathVariable int id){
		try {
			DrugReservation drugReservation =  drugReservationService.confirmReservation(id);
			return new ResponseEntity<Integer>(drugReservation.getPatient().getId(), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while confirming a reservation.", HttpStatus.BAD_REQUEST);
		}
	}
	
}

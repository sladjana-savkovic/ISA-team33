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
	public ResponseEntity<DrugReservationDTO> searchOne(@PathVariable int id,@PathVariable int doctorId){
		try {
			DrugReservation drugReservation = drugReservationService.searchOne(id,doctorId);
			if(drugReservation != null) 
				return new ResponseEntity<DrugReservationDTO>(DrugReservationMapper.toDrugReservationDTO(drugReservation),HttpStatus.OK);
			else 
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		catch(Exception exception){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> confirmReservation(@PathVariable int id){
		try {
			drugReservationService.confirmReservation(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (Exception exception) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}

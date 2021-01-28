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
			DrugReservationDTO drugReservationDTO = DrugReservationMapper.toDrugReservationDTO(drugReservationService.searchOne(id,doctorId));
			return new ResponseEntity<DrugReservationDTO>(drugReservationDTO,HttpStatus.OK);
		}
		catch(Exception exception){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> completeReservation(@PathVariable int id){
		drugReservationService.completeReservation(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}

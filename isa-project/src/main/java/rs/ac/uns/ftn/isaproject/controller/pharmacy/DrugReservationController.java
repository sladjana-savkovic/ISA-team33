package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<DrugReservationDTO> getOne(@PathVariable int id){
		try {
			DrugReservationDTO drugReservationDTO = DrugReservationMapper.toDrugReservationDTO(drugReservationService.getOne(id));
			return new ResponseEntity<DrugReservationDTO>(drugReservationDTO,HttpStatus.OK);
		}
		catch(EntityNotFoundException exception){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
}

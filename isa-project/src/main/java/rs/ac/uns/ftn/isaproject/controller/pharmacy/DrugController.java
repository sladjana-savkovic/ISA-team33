package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugQuantityPharmacyService;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugService;

@RestController
@RequestMapping(value = "api/drug")
public class DrugController {

	private DrugService drugService;
	private DrugQuantityPharmacyService drugQuantityPharmacyService;
	
	@Autowired
	public DrugController(DrugService drugService, DrugQuantityPharmacyService drugQuantityPharmacyService) {
		this.drugService = drugService;
		this.drugQuantityPharmacyService = drugQuantityPharmacyService;
	}
	
	
	@GetMapping("/pharmacy/{id}")
	public ResponseEntity<Collection<DrugDTO>> findAllByPharmacyId(@PathVariable int id){
		try {
			Collection<DrugDTO> drugDTOs = DrugMapper.toDrugDTOs(drugService.findAllByPharmacyId(id));
			return new ResponseEntity<Collection<DrugDTO>>(drugDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody DrugDTO drugDTO) {
		try {
			drugService.add(drugDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("{id}/pharmacy/{pharmacyId}/availability")
	public ResponseEntity<Boolean> checkAvailability(@PathVariable int id, @PathVariable int pharmacyId){
		try {
			boolean availability = drugService.checkAvailability(id, pharmacyId);
			return new ResponseEntity<Boolean>(availability, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/{id}/pharmacy")
	public ResponseEntity<Collection<DrugDTO>> getDrugsByPharmacyId(@PathVariable int id) {

		Collection<Drug> drugs  = drugQuantityPharmacyService.findDrugsByPharmacyId(id);

		if (drugs == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Collection<DrugDTO> drugDTOs = DrugMapper.toDrugDTOs(drugs);
		
		return new ResponseEntity<>(drugDTOs, HttpStatus.OK);
	}

}

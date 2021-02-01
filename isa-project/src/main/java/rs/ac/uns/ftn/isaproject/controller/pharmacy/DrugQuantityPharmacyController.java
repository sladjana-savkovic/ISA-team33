package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugQuantityPharmacyDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugMapper;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugQuantityPharmacyService;

@RestController
@RequestMapping(value = "api/drug-quantity-pharmacy")
public class DrugQuantityPharmacyController {

	private DrugQuantityPharmacyService quantityPharmacyService;
	
	@Autowired
	public DrugQuantityPharmacyController(DrugQuantityPharmacyService quantityPharmacyService) {
		this.quantityPharmacyService = quantityPharmacyService;
	}
	
	@GetMapping("/{drugId}/{pharmacyId}/availability")
	public ResponseEntity<Boolean> checkAvailability(@PathVariable int drugId, @PathVariable int pharmacyId){
		boolean availability = quantityPharmacyService.checkDrugAvailability(drugId, pharmacyId);
		return new ResponseEntity<Boolean>(availability, HttpStatus.OK);
	}
	
	@GetMapping("/{pharmacyId}/available")
	public ResponseEntity<Collection<DrugDTO>> findAvailableDrugsByPharmacyId(@PathVariable int pharmacyId){
		try {
			Collection<DrugDTO> drugDTOs = DrugMapper.toDrugDTOs(quantityPharmacyService.findAvailableDrugsByPharmacyId(pharmacyId));
			return new ResponseEntity<Collection<DrugDTO>>(drugDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{drugId}/{pharmacyId}/{quantity}/increase")
	public ResponseEntity<Boolean> increaseDrugQuantityPharmacy(@PathVariable int drugId, @PathVariable int pharmacyId, @PathVariable int quantity){
		boolean result = quantityPharmacyService.increaseDrugQuantityPharmacy(drugId, pharmacyId, quantity);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody DrugQuantityPharmacyDTO drugQuantityDTO) {
		try {
			quantityPharmacyService.addDrugQuantityPharmacy(drugQuantityDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{pharmacyId}")
	public ResponseEntity<Collection<DrugDTO>> findDrugsByPharmacyId(@PathVariable int pharmacyId){
		try {
			Collection<DrugDTO> drugDTOs = DrugMapper.toDrugDTOs(quantityPharmacyService.findDrugsByPharmacyId(pharmacyId));
			return new ResponseEntity<Collection<DrugDTO>>(drugDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/search/{name}")
	public ResponseEntity<Collection<DrugDTO>> searchByName(@PathVariable String name, @RequestBody ArrayList<DrugDTO> drugDTOs){
		Collection<DrugDTO> searchResult = quantityPharmacyService.searchByName(name, drugDTOs);
		return new ResponseEntity<Collection<DrugDTO>>(searchResult, HttpStatus.OK);
	}
}

package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	
	@PutMapping("/{drugId}/{pharmacyId}/{quantity}/reduce")
	public ResponseEntity<Boolean> reduceDrugQuantity(@PathVariable int drugId, @PathVariable int pharmacyId, @PathVariable int quantity){
		boolean reduceResult = quantityPharmacyService.reduceDrugQuantity(drugId, pharmacyId,quantity);
		if(reduceResult)
			return new ResponseEntity<Boolean>(reduceResult, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}

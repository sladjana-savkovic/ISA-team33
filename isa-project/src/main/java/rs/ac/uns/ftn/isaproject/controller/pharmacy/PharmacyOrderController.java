package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyOrderDTO;
import rs.ac.uns.ftn.isaproject.service.pharmacy.PharmacyOrderService;

@RestController
@RequestMapping(value = "api/order")
public class PharmacyOrderController {
	
	private PharmacyOrderService pharmacyOrderService;
	
	@Autowired
	public PharmacyOrderController(PharmacyOrderService pharmacyOrderService) {
		this.pharmacyOrderService = pharmacyOrderService;
	}
	
	@RequestMapping(path = "/drug-quantity", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> addDrugQuantity(@RequestBody DrugQuantityDTO drugQuantityDTO) {
		try {
			pharmacyOrderService.addDrugQuantity(drugQuantityDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody PharmacyOrderDTO pharmacyOrderDTO) {
		try {
			pharmacyOrderService.add(pharmacyOrderDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/last")
	public ResponseEntity<Integer> findByMaxId(){
		try {
			return new ResponseEntity<>(pharmacyOrderService.findByMaxId(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}


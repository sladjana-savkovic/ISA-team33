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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityOrderDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyOrderDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugQuantityOrderMapper;
import rs.ac.uns.ftn.isaproject.mapper.PharmacyOrderMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;
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
	public ResponseEntity<Void> addDrugQuantity(@RequestBody DrugQuantityOrderDTO drugQuantityDTO) {
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
	
	@GetMapping("/{id}")
	public ResponseEntity<PharmacyOrderDTO> getById(@PathVariable int id) {

		PharmacyOrder pharmacyOrder = pharmacyOrderService.findById(id);

		if (pharmacyOrder == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		PharmacyOrderDTO pharmacyOrderDTO = new PharmacyOrderDTO(pharmacyOrder.getId(), pharmacyOrder.getLimitDate(), pharmacyOrder.isFinished(), pharmacyOrder.getPharmacyAdministrator().getId());
		return new ResponseEntity<>(pharmacyOrderDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/drug-quantity")
	public ResponseEntity<Collection<DrugQuantityOrderDTO>> getDrugQuantityByPharmacyOrderId(@PathVariable int id) {

		Collection<DrugQuantityOrder> drugs = pharmacyOrderService.findByPharmacyOrderId(id);

		if (drugs == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Collection<DrugQuantityOrderDTO> drugDTOs = DrugQuantityOrderMapper.toDrugQuantityOrderDTOs(drugs);
		
		return new ResponseEntity<>(drugDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/pharmacy")
	public ResponseEntity<Collection<PharmacyOrderDTO>> getByPharmacyId(@PathVariable int id) {

		Collection<PharmacyOrder> pharmacyOrders = pharmacyOrderService.findByPharmacyId(id);

		if (pharmacyOrders == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Collection<PharmacyOrderDTO> pharmacyOrderDTOs = PharmacyOrderMapper.toPharmacyOrderDTOs(pharmacyOrders);
		return new ResponseEntity<>(pharmacyOrderDTOs, HttpStatus.OK);
	}
	
}


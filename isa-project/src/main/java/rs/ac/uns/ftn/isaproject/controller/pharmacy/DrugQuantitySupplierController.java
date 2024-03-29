package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.AddDrugQuantitySupplierDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugQuantitySupplierDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugQuantitySupplierMapper;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugQuantitySupplierService;

@RestController
@RequestMapping(value = "api/drug-quantity-supplier")
public class DrugQuantitySupplierController {

	private DrugQuantitySupplierService quantitySupplierService;
	
	@Autowired
	public DrugQuantitySupplierController(DrugQuantitySupplierService quantitySupplierService) {
		this.quantitySupplierService = quantitySupplierService;
	}	
	
	@GetMapping("/supplier")
	@PreAuthorize("hasRole('SUPPLIER')")
	public ResponseEntity<Collection<DrugQuantitySupplierDTO>> findBySupplierId(){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			int supplierId = u.getUser().getId();
			Collection<DrugQuantitySupplierDTO> drugQuantityDTOs = DrugQuantitySupplierMapper.toDrugQuantitySupplierDTOs(quantitySupplierService.findBySupplierId(supplierId));
			return new ResponseEntity<Collection<DrugQuantitySupplierDTO>>(drugQuantityDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('SUPPLIER')")
	public ResponseEntity<Void> add(@RequestBody AddDrugQuantitySupplierDTO drugQuantityDTO) {
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			drugQuantityDTO.supplierId = u.getUser().getId();
			quantitySupplierService.add(drugQuantityDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
}

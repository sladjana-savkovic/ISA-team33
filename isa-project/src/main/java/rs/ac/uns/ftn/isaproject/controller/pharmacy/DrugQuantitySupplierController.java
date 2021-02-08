package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantitySupplierDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugQuantitySupplierMapper;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugQuantitySupplierService;

@RestController
@RequestMapping(value = "api/drug-quantity-supplier")
public class DrugQuantitySupplierController {

	private DrugQuantitySupplierService quantitySupplierService;
	
	@Autowired
	public DrugQuantitySupplierController(DrugQuantitySupplierService quantitySupplierService) {
		this.quantitySupplierService = quantitySupplierService;
	}	
	
	@GetMapping("/{id}/supplier")
	@PreAuthorize("hasRole('SUPPLIER')")
	public ResponseEntity<Collection<DrugQuantitySupplierDTO>> findBySupplierId(@PathVariable int id){
		try {
			Collection<DrugQuantitySupplierDTO> drugQuantityDTOs = DrugQuantitySupplierMapper.toDrugQuantitySupplierDTOs(quantitySupplierService.findBySupplierId(id));
			return new ResponseEntity<Collection<DrugQuantitySupplierDTO>>(drugQuantityDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}

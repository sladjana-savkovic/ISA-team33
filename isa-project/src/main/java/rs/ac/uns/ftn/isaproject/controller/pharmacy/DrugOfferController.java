package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.DrugOfferDTO;
import rs.ac.uns.ftn.isaproject.dto.SupplierDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugOfferMapper;
import rs.ac.uns.ftn.isaproject.mapper.SupplierMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugOfferService;
import rs.ac.uns.ftn.isaproject.service.pharmacy.PharmacyOrderService;

@RestController
@RequestMapping(value = "api/drug-offer")
public class DrugOfferController {

	private DrugOfferService drugOfferService;
	private PharmacyOrderService pharmacyOrderService;
	
	@Autowired
	public DrugOfferController(DrugOfferService drugOfferService, PharmacyOrderService pharmacyOrderService) {
		this.drugOfferService = drugOfferService;
		this.pharmacyOrderService = pharmacyOrderService;
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Void> acceptOffer(@PathVariable int id){
			drugOfferService.acceptOffer(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@GetMapping("/{id}/pharmacy-order")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Collection<DrugOfferDTO>> findByPharmacyOrderId(@PathVariable int id){
		Collection<DrugOfferDTO> drugOfferDTOs =DrugOfferMapper.toDrugOfferDTOs(drugOfferService.findByPharmacyOrderId(id));
		return new ResponseEntity<Collection<DrugOfferDTO>>(drugOfferDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/pharmacy")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Collection<DrugOfferDTO>> findByPharmacyId(@PathVariable int id){
		Collection<DrugOfferDTO> drugOfferDTOs =DrugOfferMapper.toDrugOfferDTOs(drugOfferService.findByPharmacyId(id));
		return new ResponseEntity<Collection<DrugOfferDTO>>(drugOfferDTOs, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/reject")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Void> rejectOffer(@PathVariable int id){
		drugOfferService.rejectOffer(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<DrugOfferDTO> findById(@PathVariable int id){
		DrugOffer drugOffer = drugOfferService.findById(id);
		DrugOfferDTO drugOfferDTO = new DrugOfferDTO(drugOffer.getId(), drugOffer.getTotalPrice(), drugOffer.getStatus(), drugOffer.getLimitDate(), drugOffer.getPharmacyOrder().getId());
		return new ResponseEntity<DrugOfferDTO>(drugOfferDTO, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/supplier")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<SupplierDTO> findSupplierById(@PathVariable int id){
		SupplierDTO supplierDTO = SupplierMapper.toSupplierDTO(drugOfferService.findSupplierById(id));
		return new ResponseEntity<SupplierDTO>(supplierDTO, HttpStatus.OK);
	}
}

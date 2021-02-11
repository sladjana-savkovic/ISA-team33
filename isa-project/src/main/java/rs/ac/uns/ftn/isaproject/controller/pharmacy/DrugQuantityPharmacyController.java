package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddTherapyDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugQuantityPharmacyDTO;
import rs.ac.uns.ftn.isaproject.dto.NotificationDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugMapper;
import rs.ac.uns.ftn.isaproject.mapper.DrugQuantityPharmacyMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.service.notification.NotificationService;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugQuantityPharmacyService;

@RestController
@RequestMapping(value = "api/drug-quantity-pharmacy")
public class DrugQuantityPharmacyController {

	private DrugQuantityPharmacyService quantityPharmacyService;
	private NotificationService notificationService;
	
	@Autowired
	public DrugQuantityPharmacyController(DrugQuantityPharmacyService quantityPharmacyService, NotificationService notificationService) {
		this.quantityPharmacyService = quantityPharmacyService;
		this.notificationService = notificationService;
	}
	
	@GetMapping("/{drugId}/{pharmacyId}/availability")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Boolean> checkAvailability(@PathVariable int drugId, @PathVariable int pharmacyId){
		boolean availability = quantityPharmacyService.checkDrugAvailability(drugId, pharmacyId);

		if(!availability) {
			notificationService.send(new NotificationDTO(drugId, pharmacyId));
		}
		
		return new ResponseEntity<Boolean>(availability, HttpStatus.OK);
	}
	
	@GetMapping("/{pharmacyId}/available")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Collection<DrugDTO>> findAvailableDrugsByPharmacyId(@PathVariable int pharmacyId){
		try {
			Collection<DrugDTO> drugDTOs = DrugMapper.toDrugDTOs(quantityPharmacyService.findAvailableDrugsByPharmacyId(pharmacyId));
			return new ResponseEntity<Collection<DrugDTO>>(drugDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{drugId}/{pharmacyId}/{quantity}/increase")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> increaseDrugQuantityPharmacy(@PathVariable int drugId, @PathVariable int pharmacyId, @PathVariable int quantity){
		try {
			boolean result = quantityPharmacyService.increaseDrugQuantityPharmacy(drugId, pharmacyId, quantity);
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while increase drug quantity.", HttpStatus.BAD_REQUEST);
		}
		}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> add(@RequestBody DrugQuantityPharmacyDTO drugQuantityDTO) {
		try {
			quantityPharmacyService.addDrugQuantityPharmacy(drugQuantityDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while add drug in pharmacy.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{pharmacyId}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> findDrugsByPharmacyId(@PathVariable int pharmacyId){
		try {
			Collection<DrugDTO> drugDTOs = DrugMapper.toDrugDTOs(quantityPharmacyService.findDrugsByPharmacyId(pharmacyId));
			return new ResponseEntity<Collection<DrugDTO>>(drugDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting offer.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/search/{name}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> searchByName(@PathVariable String name, @RequestBody ArrayList<DrugDTO> drugDTOs){
		try {
			Collection<DrugDTO> searchResult = quantityPharmacyService.searchByName(name, drugDTOs);
			return new ResponseEntity<Collection<DrugDTO>>(searchResult, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while searching drugs in pharmacy.", HttpStatus.BAD_REQUEST);
		}
		}
	
	@PutMapping("/{drugId}/{pharmacyId}/delete")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> deleteDrugQuantityPharmacy(@PathVariable int drugId, @PathVariable int pharmacyId){
		try {
			
			if(!quantityPharmacyService.checkIfDrugCanDelete(drugId, pharmacyId)) {
				return new ResponseEntity<>("The drug is reserved.", HttpStatus.BAD_REQUEST);
			}
			
			quantityPharmacyService.deleteDrugQuantityPharmacy(drugId, pharmacyId);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while deleting drug.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/{pharmacyId}/therapies-availability", consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> checkAvailabilityOrReturnMissingDrugs(@PathVariable int pharmacyId, @RequestBody ArrayList<AddTherapyDTO> therapyDTOs){
		try {
			Collection<DrugQuantityPharmacy> missingDrugs = quantityPharmacyService.reduceDrugQuantitiesOrReturnMissingDrugs(pharmacyId, therapyDTOs);
			if(missingDrugs != null) {
				return new ResponseEntity<Collection<DrugQuantityPharmacyDTO>>(DrugQuantityPharmacyMapper.toDrugQuantityPharmacyDTOs(missingDrugs),HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while checking therapies availability.",HttpStatus.BAD_REQUEST);
		}
	}
	
}

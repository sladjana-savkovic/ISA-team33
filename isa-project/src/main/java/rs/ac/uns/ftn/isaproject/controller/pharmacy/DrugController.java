package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddDrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugSearchDTO;
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
	
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
	public ResponseEntity<Void> add(@RequestBody AddDrugDTO drugDTO) {
		try {
			drugService.add(drugDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}/pharmacy")
	@PreAuthorize("hasAnyRole('ROLE_PHARMACYADMIN', 'ROLE_PATIENT')")
	public ResponseEntity<Collection<DrugDTO>> getDrugsByPharmacyId(@PathVariable int id) {
		try {
			Collection<Drug> drugs  = drugQuantityPharmacyService.findDrugsByPharmacyId(id);

			if (drugs == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			Collection<DrugDTO> drugDTOs = DrugMapper.toDrugDTOs(drugs);
			
			return new ResponseEntity<>(drugDTOs, HttpStatus.OK);			
		}	
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@GetMapping("/{id}/substitute")
	public ResponseEntity<Collection<DrugDTO>> getSubstituteDrugs(@PathVariable int id){
		try {
			Collection<DrugDTO> drugDTOs = DrugMapper.toDrugDTOs(drugService.getSubstituteDrugs(id));
			return new ResponseEntity<Collection<DrugDTO>>(drugDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<Collection<DrugDTO>> getAllDrugs(){
		try {
			Collection<DrugDTO> drugDTOs = DrugMapper.toDrugSearchDTOs(drugService.getAllDrugs());
			return new ResponseEntity<Collection<DrugDTO>>(drugDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("{id}/one")
	public ResponseEntity<DrugDTO> getById(@PathVariable int id){
		try {
			Drug drug = drugService.getById(id);
			DrugDTO drugDTO = new DrugDTO(drug.getId(), drug.getName(), drug.getTypeOfDrug().toString(), drug.getTypeOfDrugsForm().toString(), drug.getProducer(), null, drug.getContraindication(), drug.getDailyDose(), null);
			return new ResponseEntity<DrugDTO>(drugDTO, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/search")
	public ResponseEntity<Collection<DrugDTO>> searchByNameAndGradeAndType(@RequestBody DrugSearchDTO drugDTOs){
		try {
			Collection<DrugDTO> searchResult = drugService.searchByNameAndGradeAndType(drugDTOs);
			return new ResponseEntity<Collection<DrugDTO>>(searchResult, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}	
		
}

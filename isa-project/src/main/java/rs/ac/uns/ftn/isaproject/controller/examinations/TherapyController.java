package rs.ac.uns.ftn.isaproject.controller.examinations;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddTherapyDTO;
import rs.ac.uns.ftn.isaproject.service.examinations.TherapyService;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugQuantityPharmacyService;

@RestController
@RequestMapping(value = "api/therapy")
public class TherapyController {

	private TherapyService therapyService;
	private DrugQuantityPharmacyService quantityPharmacyService;
	
	@Autowired
	public TherapyController(TherapyService therapyService,DrugQuantityPharmacyService quantityPharmacyService) {
		this.therapyService = therapyService;
		this.quantityPharmacyService = quantityPharmacyService;
	}
	
	@PostMapping("/pharmacy/{pharmacyId}")
	public ResponseEntity<Void> add(@RequestBody ArrayList<AddTherapyDTO> therapyDTOs, @PathVariable int pharmacyId){
		try {
			therapyService.add(therapyDTOs);
			for(AddTherapyDTO therapyDTO:therapyDTOs) 
				quantityPharmacyService.reduceDrugQuantity(therapyDTO.drugId, pharmacyId);
			
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
}

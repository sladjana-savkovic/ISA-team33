package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.PricelistDTO;
import rs.ac.uns.ftn.isaproject.service.pharmacy.PricelistService;

@RestController
@RequestMapping(value = "api/pricelist")
public class PricelistController {

	private PricelistService pricelistService;
	
	@Autowired
	public PricelistController(PricelistService pricelistService) {
		this.pricelistService = pricelistService;
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Void> add(@RequestBody PricelistDTO pricelistDTO) {
		try {
			pricelistService.save(pricelistDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
}

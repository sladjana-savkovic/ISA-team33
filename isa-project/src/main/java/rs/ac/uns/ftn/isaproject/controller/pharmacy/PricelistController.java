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
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyPriceDTO;
import rs.ac.uns.ftn.isaproject.dto.PricelistDTO;
import rs.ac.uns.ftn.isaproject.mapper.PharmacyPriceMapper;
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
	public ResponseEntity<Void> add(@RequestBody PricelistDTO pricelistDTO) {
		try {
			pricelistService.save(pricelistDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value = "/{id}/drug")
	public ResponseEntity<Collection<PharmacyPriceDTO>> getPharmaciesAndPriceByDrugId(@PathVariable int id) {		
		try {
			Collection<PharmacyPriceDTO> pharmacyPriceDTOs  = PharmacyPriceMapper.toPharmacyPriceDTO(pricelistService.getPharmaciesAndPriceByDrugId(id));
			return new ResponseEntity<Collection<PharmacyPriceDTO>>(pharmacyPriceDTOs, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
}

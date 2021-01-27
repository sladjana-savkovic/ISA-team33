package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.service.pharmacy.PricelistService;

@RestController
@RequestMapping(value = "api/pricelist")
public class PricelistController {

	private PricelistService pricelistService;
	
	@Autowired
	public PricelistController(PricelistService pricelistService) {
		this.pricelistService = pricelistService;
	}
}

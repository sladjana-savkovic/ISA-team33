package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugOfferService;

@RestController
@RequestMapping(value = "api/drug-offer")
public class DrugOfferController {

	private DrugOfferService drugOfferService;
	
	@Autowired
	public DrugOfferController(DrugOfferService drugOfferService) {
		this.drugOfferService = drugOfferService;
	}
}

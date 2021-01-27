package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.service.pharmacy.PharmacyOrderService;

@RestController
@RequestMapping(value = "api/order")
public class PharmacyOrderController {
	
	private PharmacyOrderService pharmacyOrderService;
	
	@Autowired
	public PharmacyOrderController(PharmacyOrderService pharmacyOrderService) {
		this.pharmacyOrderService = pharmacyOrderService;
	}
}


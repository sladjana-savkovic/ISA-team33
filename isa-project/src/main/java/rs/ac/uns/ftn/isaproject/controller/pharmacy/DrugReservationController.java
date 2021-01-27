package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugReservationService;

@RestController
@RequestMapping(value = "api/drug-reservation")
public class DrugReservationController {

	private DrugReservationService drugReservationService;
	
	@Autowired
	public DrugReservationController(DrugReservationService drugReservationService) {
		this.drugReservationService = drugReservationService;
	}
}

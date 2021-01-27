package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugService;

@RestController
@RequestMapping(value = "api/drug")
public class DrugController {

	private DrugService drugService;
	
	@Autowired
	public DrugController(DrugService drugService) {
		this.drugService = drugService;
	}
}

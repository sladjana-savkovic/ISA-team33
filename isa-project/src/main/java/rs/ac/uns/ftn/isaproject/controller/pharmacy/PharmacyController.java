package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyDTO;
import rs.ac.uns.ftn.isaproject.mapper.AppointmentMapper;
import rs.ac.uns.ftn.isaproject.mapper.DrugMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.service.pharmacy.PharmacyService;

@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyController {

	private PharmacyService pharmacyService;
	
	@Autowired
	public PharmacyController(PharmacyService pharmacyService) {
		this.pharmacyService = pharmacyService;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PharmacyDTO> getPharmacyById(@PathVariable int id) {

		Pharmacy pharmacy = pharmacyService.findOneById(id);

		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Set<DrugDTO> drugDTOs = DrugMapper.toDrugDTOs(pharmacy.getDrugs());
		Set<AppointmentDTO> appointmentDTOs = AppointmentMapper.toAppointmentDTOs(pharmacy.getAppointments());
		PharmacyDTO pharmacyDTO = new PharmacyDTO(pharmacy.getId(), pharmacy.getName(), pharmacy.getAverageGrade(), pharmacy.getAddress(), pharmacy.getCity().getId(), pharmacy.getCity().getName(), pharmacy.getCity().getCountry().getName(), drugDTOs, appointmentDTOs);
		
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);
	}
}

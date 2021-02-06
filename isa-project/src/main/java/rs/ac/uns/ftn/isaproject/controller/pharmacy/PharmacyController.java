package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyDTO;
import rs.ac.uns.ftn.isaproject.mapper.AppointmentMapper;
import rs.ac.uns.ftn.isaproject.mapper.DoctorMapper;
import rs.ac.uns.ftn.isaproject.mapper.PharmacyMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;
import rs.ac.uns.ftn.isaproject.service.pharmacy.PharmacyService;
import rs.ac.uns.ftn.isaproject.service.users.DoctorService;

@RestController
@RequestMapping(value = "api/pharmacy")
public class PharmacyController {

	private PharmacyService pharmacyService;
	private DoctorService doctorService;
	private AppointmentService appointmentService;
	
	@Autowired
	public PharmacyController(PharmacyService pharmacyService, DoctorService doctorService, AppointmentService appointmentService) {
		this.pharmacyService = pharmacyService;
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_PHARMACYADMIN', 'PATIENT')")
	public ResponseEntity<PharmacyDTO> getPharmacyById(@PathVariable int id) {

		Pharmacy pharmacy = pharmacyService.findOneById(id);

		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Collection<AppointmentDTO> appointmentDTOs = AppointmentMapper.toAppointmentDTOs(appointmentService.findAllCreatedByPharmacy(id));
		Collection<DoctorDTO> doctorDTOs = DoctorMapper.toDoctoryDTOs(doctorService.findByPharmacyId(id));
		PharmacyDTO pharmacyDTO = new PharmacyDTO(pharmacy.getId(), pharmacy.getName(), pharmacy.getAverageGrade(), pharmacy.getAddress(), pharmacy.getCity().getId(), pharmacy.getCity().getName(), pharmacy.getCity().getCountry().getName(), appointmentDTOs, doctorDTOs, pharmacy.getLatitude(), pharmacy.getLongitude());
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);
	}

		
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody PharmacyDTO pharmacyDTO){
		try {
			pharmacyService.add(pharmacyDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
			
	@GetMapping()
	public ResponseEntity<Collection<PharmacyDTO>> getAll() {
		Collection<PharmacyDTO> pharmacyDTOs = PharmacyMapper.toPharmacyDTOs(pharmacyService.findAll());
		return new ResponseEntity<Collection<PharmacyDTO>>(pharmacyDTOs, HttpStatus.OK);
	}
	
	@PostMapping("/available")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Collection<PharmacyDTO>> getPharmacies(@RequestBody AddAppointmentDTO appointmentDTO){
		LocalDateTime date = LocalDateTime.parse(appointmentDTO.startTime);
		Collection<PharmacyDTO> pharmacyDTOs = PharmacyMapper.toPharmacyDTOs(pharmacyService.findAvailablePharmacy(date));
		for(PharmacyDTO p: pharmacyDTOs) {
			p.doctors = null;
			p.appointments = null;
		}
		return new ResponseEntity<Collection<PharmacyDTO>>(pharmacyDTOs, HttpStatus.OK);
	}

}

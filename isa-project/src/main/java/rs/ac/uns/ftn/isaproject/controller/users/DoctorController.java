package rs.ac.uns.ftn.isaproject.controller.users;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddAppointmentDTO;
import rs.ac.uns.ftn.isaproject.dto.AddDermatologistDTO;
import rs.ac.uns.ftn.isaproject.dto.AddDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorPharmacyDTO;
import rs.ac.uns.ftn.isaproject.dto.ViewSearchedDoctorDTO;
import rs.ac.uns.ftn.isaproject.mapper.DoctorMapper;
import rs.ac.uns.ftn.isaproject.mapper.ViewSearchedDoctorMapper;
import rs.ac.uns.ftn.isaproject.model.utils.GradePharmacistComparator;
import rs.ac.uns.ftn.isaproject.model.utils.Order;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;
import rs.ac.uns.ftn.isaproject.service.users.DoctorService;
import rs.ac.uns.ftn.isaproject.service.users.UserAccountService;

@RestController
@RequestMapping(value = "api/doctor")
public class DoctorController {

	private DoctorService doctorService;
	private UserAccountService userAccountService;
	private AppointmentService appointmentService;
	
	@Autowired
	public DoctorController(DoctorService doctorService, AppointmentService appointmentService,UserAccountService userAccountService) {
		this.doctorService = doctorService;
		this.userAccountService = userAccountService;
		this.appointmentService = appointmentService;
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PATIENT', 'PHARMACIST')")
	public ResponseEntity<?> findOneById(@PathVariable int id) {
		try {
			DoctorDTO doctorDTO = DoctorMapper.toDoctorDTO(doctorService.getOne(id));
			doctorDTO.setEmail(userAccountService.findByUserId(id).getUsername());
			return new ResponseEntity<DoctorDTO>(doctorDTO, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>("The requested doctor doesn't exist in the database.", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> updateInfo(@RequestBody DoctorDTO doctorDTO){
		try {
			doctorService.updateInfo(doctorDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while updating doctor's information.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/pharmacies")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> doctorPharmacies(@PathVariable int id){
		try {
			Collection<DoctorPharmacyDTO> doctorPharmacyDTOs = DoctorMapper.toDoctorPharmacyDTOs(doctorService.getOne(id));
			return new ResponseEntity<Collection<DoctorPharmacyDTO>>(doctorPharmacyDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>("The requested doctor doesn't exist in the database", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/pharmacy")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> findByPharmacyId(@PathVariable int id) {
		try {
			Collection<ViewSearchedDoctorDTO> doctorDTOs = ViewSearchedDoctorMapper.toViewSearchedDoctorDTODrugDTOs(doctorService.findByPharmacyId(id));
			return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(doctorDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/search/{name}/{surname}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> searchByNameAndSurname(@PathVariable String name,@PathVariable String surname,@RequestBody ArrayList<ViewSearchedDoctorDTO> doctorDTOs){
		try {
			Collection<ViewSearchedDoctorDTO> searchResult = doctorService.searchByNameAndSurname(name, surname, doctorDTOs);
			return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(searchResult, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while searching doctor in the pharmacy.", HttpStatus.BAD_REQUEST);
		}
		}
	
	@PostMapping("/filter/{type}/{grade}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> filterByGradeAndType(@PathVariable String type,@PathVariable int grade,@RequestBody ArrayList<ViewSearchedDoctorDTO> doctorDTOs){
		try {
			Collection<ViewSearchedDoctorDTO> searchResult = doctorService.filterByGradeAndType(type, grade, doctorDTOs);
			return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(searchResult, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while filtering doctor in the pharmacy.", HttpStatus.BAD_REQUEST);
		}
		}
	
	@RequestMapping(path = "/add/pharmacist", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> add(@RequestBody AddDoctorDTO doctorDTO){
		try {
			doctorService.addPharmacist(doctorDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while adding pharmacist in the pharmacy.", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(path = "/add/dermatologist", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
	public ResponseEntity<Void> add(@RequestBody AddDermatologistDTO dermatologistDTO){
		try {
			doctorService.add(dermatologistDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping("/{id_doctor}/pharmacy/{id_pharmacy}/delete")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> deleteDoctor(@PathVariable int id_doctor, @PathVariable int id_pharmacy){
		try {
			if(!appointmentService.getDoctorScheduledAppointmentsInPharamacy(id_doctor, id_pharmacy).isEmpty()) {
				return new ResponseEntity<>("The doctor has schedule appointments in the pharmacy.", HttpStatus.BAD_REQUEST);
			}
			doctorService.deleteDoctor(id_doctor);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while deleting doctors without working time.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/pharmacy/without/working-time")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> findDoctorWithoutWorkingTime(@PathVariable int id) {
		try {
			Collection<ViewSearchedDoctorDTO> doctorDTOs = ViewSearchedDoctorMapper.toViewSearchedDoctorDTODrugDTOs(doctorService.getDoctorWithoutWorkingTime(id));
			return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(doctorDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting doctors without working time.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id_doctor}/pharmacy/{id_pharmacy}/add-dermatologist")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> addDermatologistInPharmacy(@PathVariable int id_doctor, @PathVariable int id_pharmacy){
		try {
			doctorService.addDermatologistInPharmacy(id_doctor, id_pharmacy);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while adding doctor in the pharmacy.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/not-pharmacy")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> findDoctorNotInPharmacy(@PathVariable int id) {
		try {
			Collection<ViewSearchedDoctorDTO> doctorDTOs = ViewSearchedDoctorMapper.toViewSearchedDoctorDTODrugDTOs(doctorService.findDoctorNotInPharmacy(id));
			return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(doctorDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/available/{sort}")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<Collection<DoctorDTO>> getPharmacies(@RequestBody AddAppointmentDTO appointmentDTO,@PathVariable String sort){
		LocalDateTime date = LocalDateTime.parse(appointmentDTO.startTime);
		Collection<DoctorDTO> doctorDTOs = DoctorMapper.toDoctoryDTOs(doctorService.findAvailableDoctor(date, (long)appointmentDTO.idPharmacy));
		switch (sort) {
		case "GRADE_ASC":
			((List<DoctorDTO>) doctorDTOs).sort(new GradePharmacistComparator(Order.ASC));
			break;

		case "GRADE_DESC":
			((List<DoctorDTO>) doctorDTOs).sort(new GradePharmacistComparator(Order.DESC));
			break;

		}
		return new ResponseEntity<Collection<DoctorDTO>>(doctorDTOs, HttpStatus.OK);
	}
	
	@GetMapping("/{type}/type-of-doctor")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public ResponseEntity<?> findDoctorByType(@PathVariable int type) {
		try {
			Collection<DoctorDTO> doctorDTOs = DoctorMapper.toDoctoryDTOs(doctorService.findDoctorByType(type));
			return new ResponseEntity<Collection<DoctorDTO>>(doctorDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>("The doctor doesn't exist in the database", HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}


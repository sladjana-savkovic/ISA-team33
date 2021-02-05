package rs.ac.uns.ftn.isaproject.controller.users;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
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
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import rs.ac.uns.ftn.isaproject.dto.AddDermatologistDTO;
import rs.ac.uns.ftn.isaproject.dto.AddDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.ViewSearchedDoctorDTO;
import rs.ac.uns.ftn.isaproject.dto.DoctorPharmacyDTO;
import rs.ac.uns.ftn.isaproject.mapper.DoctorMapper;
import rs.ac.uns.ftn.isaproject.mapper.ViewSearchedDoctorMapper;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;
import rs.ac.uns.ftn.isaproject.service.users.DoctorService;

@RestController
@RequestMapping(value = "api/doctor")
public class DoctorController {

	private DoctorService doctorService;
	private AppointmentService appointmentService;
	
	@Autowired
	public DoctorController(DoctorService doctorService, AppointmentService appointmentService) {
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> findOneById(@PathVariable int id) {
		try {
			DoctorDTO doctorDTO = DoctorMapper.toDoctorDTO(doctorService.getOne(id));
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
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> searchByNameAndSurname(@PathVariable String name,@PathVariable String surname,@RequestBody ArrayList<ViewSearchedDoctorDTO> doctorDTOs){
		Collection<ViewSearchedDoctorDTO> searchResult = doctorService.searchByNameAndSurname(name, surname, doctorDTOs);
		return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(searchResult, HttpStatus.OK);
	}
	
	@PostMapping("/filter/{type}/{grade}")
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> filterByGradeAndType(@PathVariable String type,@PathVariable int grade,@RequestBody ArrayList<ViewSearchedDoctorDTO> doctorDTOs){
		Collection<ViewSearchedDoctorDTO> searchResult = doctorService.filterByGradeAndType(type, grade, doctorDTOs);
		return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(searchResult, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/add/pharmacist", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody AddDoctorDTO doctorDTO){
		try {
			doctorService.addPharmacist(doctorDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(path = "/add/dermatologist", method = RequestMethod.POST, consumes = "application/json")
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
	public ResponseEntity<Void> deleteDoctor(@PathVariable int id_doctor, @PathVariable int id_pharmacy){
		if(appointmentService.getDoctorScheduledAppointmentsInPharamacy(id_doctor, id_pharmacy).isEmpty()) {
			doctorService.deleteDoctor(id_doctor);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/pharmacy/without/working-time")
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> findDoctorWithoutWorkingTime(@PathVariable int id) {
		try {
			Collection<ViewSearchedDoctorDTO> doctorDTOs = ViewSearchedDoctorMapper.toViewSearchedDoctorDTODrugDTOs(doctorService.getDoctorWithoutWorkingTime(id));
			return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(doctorDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/report")
	public ResponseEntity<Void> report(HttpServletResponse response, @PathVariable int id) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(doctorService.report(id));
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/pharmacy_report.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
		HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
		exporter.exportReport();
		return null;
		
	}
	
	@PutMapping("/{id_doctor}/pharmacy/{id_pharmacy}/add-dermatologist")
	public ResponseEntity<Void> addDermatologistInPharmacy(@PathVariable int id_doctor, @PathVariable int id_pharmacy){
		try {
			doctorService.addDermatologistInPharmacy(id_doctor, id_pharmacy);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/not-pharmacy")
	public ResponseEntity<Collection<ViewSearchedDoctorDTO>> findDoctorNotInPharmacy(@PathVariable int id) {
		try {
			Collection<ViewSearchedDoctorDTO> doctorDTOs = ViewSearchedDoctorMapper.toViewSearchedDoctorDTODrugDTOs(doctorService.findDoctorNotInPharmacy(id));
			return new ResponseEntity<Collection<ViewSearchedDoctorDTO>>(doctorDTOs, HttpStatus.OK);
		}
		catch(EntityNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
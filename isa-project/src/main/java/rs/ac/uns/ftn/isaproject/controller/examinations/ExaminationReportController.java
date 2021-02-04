package rs.ac.uns.ftn.isaproject.controller.examinations;

import java.util.ArrayList;
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
import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.AddTherapyDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.mapper.ExaminationReportMapper;
import rs.ac.uns.ftn.isaproject.mapper.ExaminedPatientMapper;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;
import rs.ac.uns.ftn.isaproject.service.examinations.ExaminationReportService;
import rs.ac.uns.ftn.isaproject.service.examinations.TherapyService;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugQuantityPharmacyService;

@RestController
@RequestMapping(value = "api/examination-report")
public class ExaminationReportController {

	private ExaminationReportService examinationReportService;
	private AppointmentService appointmentService;
	private TherapyService therapyService;
	private DrugQuantityPharmacyService quantityPharmacyService;
	
	@Autowired
	public ExaminationReportController(ExaminationReportService examinationReportService,AppointmentService appointmentService,TherapyService therapyService,
									   DrugQuantityPharmacyService quantityPharmacyService) {
		this.examinationReportService = examinationReportService;
		this.appointmentService = appointmentService;
		this.therapyService = therapyService;
		this.quantityPharmacyService = quantityPharmacyService;
	}
	
	@GetMapping("/doctor/{id}")
	public ResponseEntity<Collection<ExaminedPatientDTO>> findAllByDoctorIdOrderByDate(@PathVariable int id){
		Collection<ExaminedPatientDTO> examinationReports = 
				ExaminedPatientMapper.toExaminedPatientDTOs(examinationReportService.findAllFinishedByDoctorId(id));
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(examinationReports, HttpStatus.OK);
	}
	
	@PostMapping("/search/{name}/{surname}")
	public ResponseEntity<Collection<ExaminedPatientDTO>> searchByNameAndSurname(@PathVariable String name,@PathVariable String surname,@RequestBody ArrayList<ExaminedPatientDTO> examinedPatientDTOs){
		Collection<ExaminedPatientDTO> searchResult = examinationReportService.searchByNameAndSurname(name, surname, examinedPatientDTOs);
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(searchResult, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> add(@RequestBody AddExaminationReportDTO examinationReportDTO){
		try {
			ExaminationReport examinationReport = examinationReportService.add(examinationReportDTO);
			therapyService.add(examinationReportDTO.therapyDTOs, examinationReport.getId());
			for(AddTherapyDTO therapyDTO:examinationReportDTO.therapyDTOs) 
				quantityPharmacyService.reduceDrugQuantity(therapyDTO.drugId, examinationReportDTO.pharmacyId);
			appointmentService.changeStatus(examinationReportDTO.appointmentId, AppointmentStatus.Finished);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (BadRequestException e) {
			return new ResponseEntity<>("A report already exists for the selected appointment.",HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while saving the examination report.",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/patient/{patientId}/doctor/{doctorId}")
	public ResponseEntity<?> getByPatientAtDoctor(@PathVariable int patientId, @PathVariable int doctorId){
		try {
			Collection<ExaminationReportDTO> examinationReportDTOs = ExaminationReportMapper.toExaminationReportDTO(examinationReportService.getByPatientAtDoctor(patientId, doctorId));
			return new ResponseEntity<Collection<ExaminationReportDTO>>(examinationReportDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("The patient hasn't had any examinations.",HttpStatus.NOT_FOUND);
		}
	}
}

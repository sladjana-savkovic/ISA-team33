package rs.ac.uns.ftn.isaproject.controller.examinations;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugQuantityPharmacyDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
import rs.ac.uns.ftn.isaproject.dto.NotificationDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.mapper.DrugQuantityPharmacyMapper;
import rs.ac.uns.ftn.isaproject.mapper.ExaminationReportMapper;
import rs.ac.uns.ftn.isaproject.mapper.ExaminedPatientMapper;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;
import rs.ac.uns.ftn.isaproject.service.examinations.ExaminationReportService;
import rs.ac.uns.ftn.isaproject.service.examinations.TherapyService;
import rs.ac.uns.ftn.isaproject.service.notification.NotificationService;
import rs.ac.uns.ftn.isaproject.service.pharmacy.DrugQuantityPharmacyService;

@RestController
@RequestMapping(value = "api/examination-report")
public class ExaminationReportController {

	private ExaminationReportService examinationReportService;
	private AppointmentService appointmentService;
	private TherapyService therapyService;
	private DrugQuantityPharmacyService quantityPharmacyService;
	private NotificationService notificationService;
	
	@Autowired
	public ExaminationReportController(ExaminationReportService examinationReportService,AppointmentService appointmentService,TherapyService therapyService,
									   DrugQuantityPharmacyService quantityPharmacyService, NotificationService notificationService) {
		this.examinationReportService = examinationReportService;
		this.appointmentService = appointmentService;
		this.therapyService = therapyService;
		this.quantityPharmacyService = quantityPharmacyService;
		this.notificationService = notificationService;
	}
	
	@GetMapping("/doctor/{id}/status/{status}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Collection<ExaminedPatientDTO>> findAllByDoctorIdAndStatus(@PathVariable int id, @PathVariable int status){
		Collection<ExaminedPatientDTO> examinationReports = 
				ExaminedPatientMapper.toExaminedPatientDTOs(examinationReportService.findAllByDoctorIdAndStatus(id, status));
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(examinationReports, HttpStatus.OK);
	}
	
	@PostMapping("/doctor/{id}/unexamined")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Collection<ExaminedPatientDTO>> findUnexaminedByDoctorId(@PathVariable int id, @RequestBody ArrayList<ExaminedPatientDTO> examinedPatients){
		Collection<Integer> patientIds = ExaminedPatientMapper.toPatientIds(examinedPatients);
		Collection<ExaminedPatientDTO> examinationReports = ExaminedPatientMapper.toExaminedPatientDTOsFromAppointments(appointmentService.getAppointmentsForUnexaminedPatientsByDoctor(id, patientIds));
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(examinationReports, HttpStatus.OK);
	}
	
	@PostMapping("/search/{name}/{surname}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<Collection<ExaminedPatientDTO>> searchByNameAndSurname(@PathVariable String name,@PathVariable String surname,@RequestBody ArrayList<ExaminedPatientDTO> examinedPatientDTOs){
		Collection<ExaminedPatientDTO> searchResult = examinationReportService.searchByNameAndSurname(name, surname, examinedPatientDTOs);
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(searchResult, HttpStatus.OK);
	}
	
	@PostMapping("/sort/date/{sortingType}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public  ResponseEntity<Collection<ExaminedPatientDTO>> sortByDate(@PathVariable String sortingType,@RequestBody ArrayList<ExaminedPatientDTO> examinedPatientDTOs){
		Collection<ExaminedPatientDTO> sortResult = examinationReportService.sortByDate(sortingType, examinedPatientDTOs);
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(sortResult, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> add(@RequestBody AddExaminationReportDTO examinationReportDTO){
		try {
			Collection<DrugQuantityPharmacy> missingDrugs = quantityPharmacyService.reduceDrugQuantitiesOrReturnMissingDrugs(examinationReportDTO.pharmacyId, examinationReportDTO.therapyDTOs);
			if(missingDrugs != null) {
				sendNotifications(missingDrugs);
				return new ResponseEntity<Collection<DrugQuantityPharmacyDTO>>(DrugQuantityPharmacyMapper.toDrugQuantityPharmacyDTOs(missingDrugs),HttpStatus.OK);
			}
			ExaminationReport examinationReport = examinationReportService.add(examinationReportDTO);
			therapyService.add(examinationReportDTO.therapyDTOs, examinationReport.getId());
			appointmentService.changeStatus(examinationReportDTO.appointmentId, AppointmentStatus.Finished);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (BadRequestException e) {
			return new ResponseEntity<>("A report already exists for the selected appointment.",HttpStatus.BAD_REQUEST);
		}
		catch (ObjectOptimisticLockingFailureException e) {
			return new ResponseEntity<>("Someone has reduced the amount of drug you have chosen. Please try saving your report again.", HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while saving the examination report.",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/patient/{patientId}/doctor/{doctorId}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> getByPatientAtDoctor(@PathVariable int patientId, @PathVariable int doctorId){
		try {
			Collection<ExaminationReportDTO> examinationReportDTOs = ExaminationReportMapper.toExaminationReportDTO(examinationReportService.getByPatientAtDoctor(patientId, doctorId));
			return new ResponseEntity<Collection<ExaminationReportDTO>>(examinationReportDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("The patient hasn't had any examinations.",HttpStatus.NOT_FOUND);
		}
	}
	
	private void sendNotifications(Collection<DrugQuantityPharmacy> missingDrugs) {
		Collection<NotificationDTO> notificationDTOs = new ArrayList<>();
		for(DrugQuantityPharmacy missingDrug:missingDrugs) {
			notificationDTOs.add(new NotificationDTO(missingDrug.getDrug().getId(), missingDrug.getPharmacy().getId()));
		}
		notificationService.sendAll(notificationDTOs);
	}
	
}

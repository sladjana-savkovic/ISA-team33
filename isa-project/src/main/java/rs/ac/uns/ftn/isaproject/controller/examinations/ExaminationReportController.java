package rs.ac.uns.ftn.isaproject.controller.examinations;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugQuantityPharmacyDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.NotificationDTO;
import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.mapper.DrugQuantityPharmacyMapper;
import rs.ac.uns.ftn.isaproject.mapper.ExaminationReportMapper;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
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
	
	@GetMapping("/patient/{patientId}/doctor")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public ResponseEntity<?> getByPatientAtDoctor(@PathVariable int patientId){
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<ExaminationReportDTO> examinationReportDTOs = ExaminationReportMapper.toExaminationReportDTO(examinationReportService.getByPatientAtDoctor(patientId, u.getUser().getId()));
			return new ResponseEntity<Collection<ExaminationReportDTO>>(examinationReportDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>("The patient hasn't had any examinations.",HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/sort/date/{sortingType}")
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	public  ResponseEntity<Collection<ExaminationReportDTO>> sortByDate(@PathVariable String sortingType,@RequestBody ArrayList<ExaminationReportDTO> examinationReportDTOs){
		Collection<ExaminationReportDTO> sortResult = examinationReportService.sortByDate(sortingType, examinationReportDTOs);
		return new ResponseEntity<Collection<ExaminationReportDTO>>(sortResult, HttpStatus.OK);
	}
	
	private void sendNotifications(Collection<DrugQuantityPharmacy> missingDrugs) {
		Collection<NotificationDTO> notificationDTOs = new ArrayList<>();
		for(DrugQuantityPharmacy missingDrug:missingDrugs) {
			notificationDTOs.add(new NotificationDTO(missingDrug.getDrug().getId(), missingDrug.getPharmacy().getId()));
		}
		notificationService.sendAll(notificationDTOs);
	}
	
}

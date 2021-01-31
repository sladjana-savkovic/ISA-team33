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
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
import rs.ac.uns.ftn.isaproject.mapper.ExaminedPatientMapper;
import rs.ac.uns.ftn.isaproject.model.enums.AppointmentStatus;
import rs.ac.uns.ftn.isaproject.service.examinations.AppointmentService;
import rs.ac.uns.ftn.isaproject.service.examinations.ExaminationReportService;

@RestController
@RequestMapping(value = "api/examination-report")
public class ExaminationReportController {

	private ExaminationReportService examinationReportService;
	private AppointmentService appointmentService;
	
	@Autowired
	public ExaminationReportController(ExaminationReportService examinationReportService,AppointmentService appointmentService) {
		this.examinationReportService = examinationReportService;
		this.appointmentService = appointmentService;
	}
	
	@GetMapping("/doctor/{id}")
	public ResponseEntity<Collection<ExaminedPatientDTO>> findAllByDoctorIdOrderByDate(@PathVariable int id){
		Collection<ExaminedPatientDTO> examinationReports = 
				ExaminedPatientMapper.toExaminedPatientDTOs(examinationReportService.findAllByDoctorId(id));
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(examinationReports, HttpStatus.OK);
	}
	
	@PostMapping("/search/{name}/{surname}")
	public ResponseEntity<Collection<ExaminedPatientDTO>> searchByNameAndSurname(@PathVariable String name,@PathVariable String surname,@RequestBody ArrayList<ExaminedPatientDTO> examinedPatientDTOs){
		Collection<ExaminedPatientDTO> searchResult = examinationReportService.searchByNameAndSurname(name, surname, examinedPatientDTOs);
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(searchResult, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody AddExaminationReportDTO examinationReportDTO){
		try {
			examinationReportService.add(examinationReportDTO);
			appointmentService.changeStatus(examinationReportDTO.appointmentId, AppointmentStatus.Finished);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
}

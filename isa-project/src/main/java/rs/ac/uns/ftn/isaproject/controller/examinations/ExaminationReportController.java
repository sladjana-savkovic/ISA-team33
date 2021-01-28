package rs.ac.uns.ftn.isaproject.controller.examinations;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
import rs.ac.uns.ftn.isaproject.mapper.ExaminedPatientMapper;
import rs.ac.uns.ftn.isaproject.service.examinations.ExaminationReportService;

@RestController
@RequestMapping(value = "api/examination-report")
public class ExaminationReportController {

	private ExaminationReportService examinationReportService;
	
	@Autowired
	public ExaminationReportController(ExaminationReportService examinationReportService) {
		this.examinationReportService = examinationReportService;
	}
	
	@GetMapping("/doctor/{id}")
	public ResponseEntity<Collection<ExaminedPatientDTO>> findAllByDoctorIdOrderByDate(@PathVariable int id){
		Collection<ExaminedPatientDTO> examinationReports = 
				ExaminedPatientMapper.toExaminedPatientDTOs(examinationReportService.findAllByDoctorId(id));
		return new ResponseEntity<Collection<ExaminedPatientDTO>>(examinationReports, HttpStatus.OK);
	}
}

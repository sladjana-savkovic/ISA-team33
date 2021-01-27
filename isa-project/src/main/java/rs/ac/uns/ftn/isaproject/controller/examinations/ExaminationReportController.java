package rs.ac.uns.ftn.isaproject.controller.examinations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.service.examinations.ExaminationReportService;

@RestController
@RequestMapping(value = "api/examination-report")
public class ExaminationReportController {

	private ExaminationReportService examinationReportService;
	
	@Autowired
	public ExaminationReportController(ExaminationReportService examinationReportService) {
		this.examinationReportService = examinationReportService;
	}
}

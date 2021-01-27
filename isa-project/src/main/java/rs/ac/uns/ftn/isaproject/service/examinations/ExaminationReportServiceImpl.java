package rs.ac.uns.ftn.isaproject.service.examinations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.repository.examinations.ExaminationReportRepository;

@Service
public class ExaminationReportServiceImpl implements ExaminationReportService {

	private ExaminationReportRepository examinationReportRepository;
	
	@Autowired
	public ExaminationReportServiceImpl(ExaminationReportRepository examinationReportRepository) {
		this.examinationReportRepository = examinationReportRepository;
	}
}

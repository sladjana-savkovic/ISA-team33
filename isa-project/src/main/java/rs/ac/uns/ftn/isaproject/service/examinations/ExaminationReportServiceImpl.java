package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;
import rs.ac.uns.ftn.isaproject.repository.examinations.ExaminationReportRepository;

@Service
public class ExaminationReportServiceImpl implements ExaminationReportService {

	private ExaminationReportRepository examinationReportRepository;
	
	@Autowired
	public ExaminationReportServiceImpl(ExaminationReportRepository examinationReportRepository) {
		this.examinationReportRepository = examinationReportRepository;
	}

	@Override
	public Collection<ExaminationReport> findAllByDoctorId(int id) {
	
		Collection<ExaminationReport> examinationReports = examinationReportRepository.findAllByDoctorId(id);
		Collection<Integer> patientsId = new ArrayList<>();
		Collection<ExaminationReport> uniqueReportsByPatient = new ArrayList<>();
		
		for(ExaminationReport report:examinationReports) {
			if(!patientsId.contains(report.getAppointment().getPatient().getId())) {
				uniqueReportsByPatient.add(report);
				patientsId.add(report.getAppointment().getPatient().getId());
			}
		}
		
		return uniqueReportsByPatient;
	}

}

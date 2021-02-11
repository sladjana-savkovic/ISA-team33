package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.model.examinations.ExaminationReport;
import rs.ac.uns.ftn.isaproject.repository.examinations.AppointmentRepository;
import rs.ac.uns.ftn.isaproject.repository.examinations.ExaminationReportRepository;

@Service
public class ExaminationReportServiceImpl implements ExaminationReportService {

	private ExaminationReportRepository examinationReportRepository;
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	public ExaminationReportServiceImpl(ExaminationReportRepository examinationReportRepository,AppointmentRepository appointmentRepository) {
		this.examinationReportRepository = examinationReportRepository;
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public Collection<ExaminationReport> getByPatientAtDoctor(int patientId, int doctorId) {
		return examinationReportRepository.getByPatientAtDoctor(patientId, doctorId);
	}

	
	@Override
	public ExaminationReport add(AddExaminationReportDTO examinationReportDTO) {
		Appointment appointment = appointmentRepository.getOne(examinationReportDTO.appointmentId);
		ExaminationReport examinationReport = new ExaminationReport();
		
		examinationReport.setDiagnosis(examinationReportDTO.diagnosis);
		examinationReport.setAppointment(appointment);
		
		return examinationReportRepository.save(examinationReport);
	}


	@Override
	public Collection<ExaminationReportDTO> sortByDate(String sortingType, ArrayList<ExaminationReportDTO> examinationReportDTOs) {
		if(sortingType.equals("asc")) {
			examinationReportDTOs.sort((a,b)->a.dateTime.compareTo(b.dateTime));
		}else {
			examinationReportDTOs.sort((a,b)->b.dateTime.compareTo(a.dateTime));
			
		}
		return examinationReportDTOs;
	}	

}

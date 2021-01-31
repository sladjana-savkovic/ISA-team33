package rs.ac.uns.ftn.isaproject.service.examinations;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddExaminationReportDTO;
import rs.ac.uns.ftn.isaproject.dto.ExaminedPatientDTO;
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

	@Override
	public void add(AddExaminationReportDTO examinationReportDTO) {
		Appointment appointment = appointmentRepository.getOne(examinationReportDTO.appointmentId);
		ExaminationReport examinationReport = new ExaminationReport();
		
		examinationReport.setDiagnosis(examinationReportDTO.diagnosis);
		examinationReport.setAppointment(appointment);
		
		examinationReportRepository.save(examinationReport);
	}

	@Override
	public Collection<ExaminedPatientDTO> searchByNameAndSurname(String name, String surname,Collection<ExaminedPatientDTO> examinedPatientDTOs) {
		Collection<ExaminedPatientDTO> searchResult = new ArrayList<>();
		
		if(name.equals("&")) name = "";
		if(surname.equals("&")) surname = "";
		
		for(ExaminedPatientDTO patient:examinedPatientDTOs) {
			if(patient.name.toLowerCase().contains(name.toLowerCase()) && patient.surname.toLowerCase().contains(surname.toLowerCase())) {
				searchResult.add(patient);
			}
		}
		return searchResult;
	}

}

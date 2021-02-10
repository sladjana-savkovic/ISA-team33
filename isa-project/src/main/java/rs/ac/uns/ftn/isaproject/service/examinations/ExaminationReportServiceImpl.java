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
	public Collection<ExaminationReport> findAllByDoctorIdAndStatus(int doctorId, int status) {
		
		if(status == 1) { //Scheduled
			Collection<ExaminationReport> examinationReportsByDoctor = examinationReportRepository.findAllFinishedByDoctor(doctorId);
			Collection<Appointment> scheduledAppointment = appointmentRepository.getScheduledAppointmentsByDoctor(doctorId);
			Collection<Integer> doctorExaminedPatientIds = new ArrayList<Integer>();
			Collection<ExaminationReport> allReports = new ArrayList<ExaminationReport>();
			
			for(ExaminationReport report:examinationReportsByDoctor) {
				if(!doctorExaminedPatientIds.contains(report.getAppointment().getPatient().getId())) {
					doctorExaminedPatientIds.add(report.getAppointment().getPatient().getId());
				}
			}
			
			for(Appointment appointment:scheduledAppointment) {
				if(!doctorExaminedPatientIds.contains(appointment.getPatient().getId())) {
					allReports.addAll(examinationReportRepository.findAllExaminationReportByPatient(appointment.getPatient().getId()));
				}
			}
			
			return findUniqueReportsByPatient(allReports);
		}else if(status == 3){ //Finished
			Collection<ExaminationReport> examinationReports = examinationReportRepository.findAllFinishedByDoctor(doctorId);
			return findUniqueReportsByPatient(examinationReports);
		}
		
		return new ArrayList<ExaminationReport>();
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
	
	@Override
	public Collection<ExaminedPatientDTO> sortByDate(String sortingType, ArrayList<ExaminedPatientDTO> examinedPatientDTOs) {
		if(sortingType.equals("asc")) {
			examinedPatientDTOs.sort((a,b)->a.dateOfLastExamination.compareTo(b.dateOfLastExamination));
		}else {
			examinedPatientDTOs.sort((a,b)->b.dateOfLastExamination.compareTo(a.dateOfLastExamination));
			
		}
		return examinedPatientDTOs;
	}

	@Override
	public Collection<ExaminationReport> getByPatientAtDoctor(int patientId, int doctorId) {
		return examinationReportRepository.getByPatientAtDoctor(patientId, doctorId);
	}

	private Collection<ExaminationReport> findUniqueReportsByPatient(Collection<ExaminationReport> examinationReports){
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
	public ExaminationReport add(AddExaminationReportDTO examinationReportDTO) {
		Appointment appointment = appointmentRepository.getOne(examinationReportDTO.appointmentId);
		ExaminationReport examinationReport = new ExaminationReport();
		
		examinationReport.setDiagnosis(examinationReportDTO.diagnosis);
		examinationReport.setAppointment(appointment);
		
		return examinationReportRepository.save(examinationReport);
	}	

}

package rs.ac.uns.ftn.isaproject.service.complaint;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.ComplaintDTO;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintDoctor;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintPharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.repository.complaint.ComplaintDoctorRepository;
import rs.ac.uns.ftn.isaproject.repository.complaint.ComplaintPharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.examinations.AppointmentRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugReservationRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.users.UserAccountRepository;

@Service
class ComplaintServiceImpl implements ComplaintService {

	private ComplaintDoctorRepository complaintDoctorRepository;
	private ComplaintPharmacyRepository complaintPharmacyRepository;
	private UserAccountRepository userAccountRepository;
	private PharmacyRepository pharmacyRepository;
	private AppointmentRepository appointmentRepository;
	private DrugReservationRepository drugReservationRepository;
	
	
	@Autowired
	public ComplaintServiceImpl(ComplaintDoctorRepository complaintDoctorRepository, ComplaintPharmacyRepository complaintPharmacyRepository,
									UserAccountRepository userAccountRepository, PharmacyRepository pharmacyRepository, AppointmentRepository appointmentRepository,
									DrugReservationRepository drugReservationRepository) {
		this.complaintDoctorRepository = complaintDoctorRepository;
		this.complaintPharmacyRepository = complaintPharmacyRepository;
		this.userAccountRepository = userAccountRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.appointmentRepository = appointmentRepository;
		this.drugReservationRepository = drugReservationRepository;
	}

	@Override
	public void addComplaintToDoctor(ComplaintDTO complaintDTO) throws Exception {
		ComplaintDoctor complaintDoctor = new ComplaintDoctor();
		
		if (appointmentRepository.getFinishedAppointmentsByPatientAndDoctor(complaintDTO.patientId, complaintDTO.subjectId) == 0) {
			throw new Exception("You did not have an appointment with your chosen doctor.");
		}
		
		UserAccount doctor = userAccountRepository.getOneByUserId(complaintDTO.subjectId);
		UserAccount patient = userAccountRepository.getOneByUserId(complaintDTO.patientId);
		complaintDoctor.setPatient(patient);
		complaintDoctor.setDoctor(doctor);
		complaintDoctor.setAnswered(false);
		complaintDoctor.setContent(complaintDTO.content);	
		complaintDoctorRepository.save(complaintDoctor);
	}

	@Override
	public void addComplaintToPharmacy(ComplaintDTO complaintDTO) throws Exception {
		ComplaintPharmacy complaintPharmacy = new ComplaintPharmacy();
		
		if (appointmentRepository.getNumberOfFinishedAppointmentsByPatientAndPharmacy(complaintDTO.patientId, complaintDTO.subjectId) == 0
				&& drugReservationRepository.getNumberOfReservationByPatientAndPharmacy(complaintDTO.patientId, complaintDTO.subjectId) == 0) {
			throw new Exception("You did not have any activities related to the selected pharmacy.");
		}
				
		Pharmacy pharmacy = pharmacyRepository.getOne(complaintDTO.subjectId);
		UserAccount patient = userAccountRepository.getOneByUserId(complaintDTO.patientId);
		complaintPharmacy.setPatient(patient);
		complaintPharmacy.setPharmacy(pharmacy);
		complaintPharmacy.setAnswered(false);
		complaintPharmacy.setContent(complaintDTO.content);		
		complaintPharmacyRepository.save(complaintPharmacy);
	}

	@Override
	public Collection<ComplaintDoctor> getUnansweredComplaintsDoctor() {
		return complaintDoctorRepository.getAllByAnsweredAttribute(false);
	}

	@Override
	public Collection<ComplaintPharmacy> getUnansweredComplaintsPharmacy() {
		return complaintPharmacyRepository.getAllByAnsweredAttribute(false);
	}

	@Override
	public void replyToComplaintDoctor(int complaintId) {
		ComplaintDoctor complaintDoctor = complaintDoctorRepository.getOne(complaintId);
		complaintDoctor.setAnswered(true);
		complaintDoctorRepository.save(complaintDoctor);
	}

	@Override
	public void replyToComplaintPharmacy(int complaintId) {
		ComplaintPharmacy complaintPharmacy = complaintPharmacyRepository.getOne(complaintId);
		complaintPharmacy.setAnswered(true);
		complaintPharmacyRepository.save(complaintPharmacy);
	}
	
	
}

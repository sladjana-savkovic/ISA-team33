package rs.ac.uns.ftn.isaproject.service.complaint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.ComplaintDTO;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintDoctor;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintPharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.repository.complaint.ComplaintDoctorRepository;
import rs.ac.uns.ftn.isaproject.repository.complaint.ComplaintPharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.users.UserAccountRepository;

@Service
class ComplaintServiceImpl implements ComplaintService {

	private ComplaintDoctorRepository complaintDoctorRepository;
	private ComplaintPharmacyRepository complaintPharmacyRepository;
	private UserAccountRepository userAccountRepository;
	private PharmacyRepository pharmacyRepository;
	
	
	@Autowired
	public ComplaintServiceImpl(ComplaintDoctorRepository complaintDoctorRepository, ComplaintPharmacyRepository complaintPharmacyRepository,
									UserAccountRepository userAccountRepository, PharmacyRepository pharmacyRepository) {
		this.complaintDoctorRepository = complaintDoctorRepository;
		this.complaintPharmacyRepository = complaintPharmacyRepository;
		this.userAccountRepository = userAccountRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public void addComplaintToDoctor(ComplaintDTO complaintDTO) {
		ComplaintDoctor complaintDoctor = new ComplaintDoctor();
		
		//fali uslov
		
		UserAccount doctor = userAccountRepository.getOneByUserId(complaintDTO.subjectId);
		UserAccount patient = userAccountRepository.getOneByUserId(complaintDTO.patientId);
		complaintDoctor.setPatient(patient);
		complaintDoctor.setDoctor(doctor);
		complaintDoctor.setAnswered(false);
		complaintDoctor.setContent(complaintDTO.content);
		
		complaintDoctorRepository.save(complaintDoctor);
	}

	@Override
	public void addComplaintToPharmacy(ComplaintDTO complaintDTO) {
		ComplaintPharmacy complaintPharmacy = new ComplaintPharmacy();
		
		//fali uslov
		
		Pharmacy pharmacy = pharmacyRepository.getOne(complaintDTO.subjectId);
		UserAccount patient = userAccountRepository.getOneByUserId(complaintDTO.patientId);
		complaintPharmacy.setPatient(patient);
		complaintPharmacy.setPharmacy(pharmacy);
		complaintPharmacy.setAnswered(false);
		complaintPharmacy.setContent(complaintDTO.content);
		
		complaintPharmacyRepository.save(complaintPharmacy);
	}

	
	
}

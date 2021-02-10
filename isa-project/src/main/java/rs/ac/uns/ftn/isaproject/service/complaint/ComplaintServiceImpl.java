package rs.ac.uns.ftn.isaproject.service.complaint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.repository.complaint.ComplaintDoctorRepository;
import rs.ac.uns.ftn.isaproject.repository.complaint.ComplaintPharmacyRepository;

@Service
class ComplaintServiceImpl implements ComplaintService {

	private ComplaintDoctorRepository complaintDoctorRepository;
	private ComplaintPharmacyRepository complaintPharmacyRepository;
	
	@Autowired
	public ComplaintServiceImpl(ComplaintDoctorRepository complaintDoctorRepository, ComplaintPharmacyRepository complaintPharmacyRepository) {
		this.complaintDoctorRepository = complaintDoctorRepository;
		this.complaintPharmacyRepository = complaintPharmacyRepository;
	}
	
	
}

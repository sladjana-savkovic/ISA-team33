package rs.ac.uns.ftn.isaproject.service.complaint;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.ComplaintDTO;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintDoctor;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintPharmacy;

public interface ComplaintService {

	void addComplaintToDoctor(ComplaintDTO complaintDTO);
	void addComplaintToPharmacy(ComplaintDTO complaintDTO);
	Collection<ComplaintDoctor> getUnansweredComplaintsDoctor();
	Collection<ComplaintPharmacy> getUnansweredComplaintsPharmacy();
	void replyToComplaintDoctor(int complaintId);
	void replyToComplaintPharmacy(int complaintId);
	
}

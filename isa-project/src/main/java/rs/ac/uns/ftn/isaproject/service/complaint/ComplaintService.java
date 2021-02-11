package rs.ac.uns.ftn.isaproject.service.complaint;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.ComplaintDTO;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintDoctor;
import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintPharmacy;

public interface ComplaintService {

	void addComplaintToDoctor(ComplaintDTO complaintDTO) throws Exception;
	void addComplaintToPharmacy(ComplaintDTO complaintDTO) throws Exception;
	Collection<ComplaintDoctor> getUnansweredComplaintsDoctor();
	Collection<ComplaintPharmacy> getUnansweredComplaintsPharmacy();
	void replyToComplaintDoctor(int complaintId);
	void replyToComplaintPharmacy(int complaintId);
	
}

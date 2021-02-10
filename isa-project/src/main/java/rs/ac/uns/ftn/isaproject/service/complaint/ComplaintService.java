package rs.ac.uns.ftn.isaproject.service.complaint;

import rs.ac.uns.ftn.isaproject.dto.ComplaintDTO;

public interface ComplaintService {

	void addComplaintToDoctor(ComplaintDTO complaintDTO);
	void addComplaintToPharmacy(ComplaintDTO complaintDTO);
}

package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;
import java.util.Map;

public interface ReportService {

	public Collection<Map<String, Object>> reportAppointment(int idPharmacy);
	public Collection<Map<String, Object>> reportDrug(int idPharmacy);
	
}

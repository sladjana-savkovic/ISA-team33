package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public interface ReportService {

	public Collection<Map<String, Object>> reportPharmacy(int idPharmacy);
	public Collection<Map<String, Object>> reportAppointment(int idPharmacy);
	public Collection<Map<String, Object>> reportDrug(int idPharmacy);
	public Collection<Map<String, Object>> reportIncomePharmacy(int idPharmacy, LocalDate start, LocalDate end);
}

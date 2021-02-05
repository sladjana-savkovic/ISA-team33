package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;
import java.util.Map;

public interface ReportService {

	public Collection<Map<String, Object>> report(int idPharmacy);
}

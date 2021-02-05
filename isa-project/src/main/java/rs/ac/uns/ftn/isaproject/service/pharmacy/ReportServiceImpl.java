package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.repository.examinations.AppointmentRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;

@Service
public class ReportServiceImpl implements ReportService{

	private PharmacyRepository pharmacyRepository;
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	public ReportServiceImpl(PharmacyRepository pharmacyRepository, AppointmentRepository appointmentRepository) {
		this.pharmacyRepository = pharmacyRepository;
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public Collection<Map<String, Object>> report(int idPharmacy) {
		Collection<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Pharmacy pharmacy = pharmacyRepository.getOne(idPharmacy);
		for(Appointment a : appointmentRepository.getAppointmentsInPharamacy(idPharmacy)) {
			
		}
		Map<String, Object> item = new HashMap<>();
		item.put("zakazano", 33);
		item.put("otkazano", 44);
		result.add(item);
		return result;
	}
	
}

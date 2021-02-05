package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.repository.examinations.AppointmentRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityPharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;

@Service
public class ReportServiceImpl implements ReportService{

	private PharmacyRepository pharmacyRepository;
	private AppointmentRepository appointmentRepository;
	private DrugQuantityPharmacyRepository drugRepository;
	
	@Autowired
	public ReportServiceImpl(PharmacyRepository pharmacyRepository, AppointmentRepository appointmentRepository, DrugQuantityPharmacyRepository drugRepository) {
		this.pharmacyRepository = pharmacyRepository;
		this.appointmentRepository = appointmentRepository;
		this.drugRepository = drugRepository;
	}

	@Override
	public Collection<Map<String, Object>> reportAppointment(int idPharmacy) {
		Collection<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Collection<Doctor> doctors = new ArrayList<Doctor>();
		Pharmacy pharmacy = pharmacyRepository.getOne(idPharmacy);
		for(Appointment a : appointmentRepository.getAppointmentsInPharamacy(idPharmacy)) {
			if(!doctors.contains(a.getDoctor())) {
			Map<String, Object> item = new HashMap<>();
			item.put("id", a.getId());
			item.put("pharmacyName", pharmacy.getName());
			item.put("price", a.getPrice());
			item.put("doctorSurname", "Dr. " + a.getDoctor().getSurname());
			result.add(item);
			doctors.add(a.getDoctor());
			}
		}
		
		return result;
	}

	@Override
	public Collection<Map<String, Object>> reportDrug(int idPharmacy) {
		Collection<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Collection<Drug> drugs = new ArrayList<Drug>();
		Pharmacy pharmacy = pharmacyRepository.getOne(idPharmacy);
		for(DrugQuantityPharmacy d : drugRepository.findByPharmacyId(idPharmacy)) {
			if(!drugs.contains(d.getDrug())) {
				Map<String, Object> item = new HashMap<>();
				item.put("id", d.getId());
				item.put("pharmacyName", pharmacy.getName());
				item.put("quantity", d.getQuantity());
				item.put("drugName", d.getDrug().getName());
				result.add(item);
				drugs.add(d.getDrug());
			}
		}
		return result;
	}
	
}

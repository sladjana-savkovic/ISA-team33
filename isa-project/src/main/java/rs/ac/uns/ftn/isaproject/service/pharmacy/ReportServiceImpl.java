package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.examinations.Appointment;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pricelist;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.repository.examinations.AppointmentRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityPharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugReservationRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PricelistRepository;

@Service
public class ReportServiceImpl implements ReportService{

	private PharmacyRepository pharmacyRepository;
	private AppointmentRepository appointmentRepository;
	private DrugQuantityPharmacyRepository drugRepository;
	private DrugReservationRepository reservationRepository;
	private PricelistRepository pricelistRepository;
	
	@Autowired
	public ReportServiceImpl(PharmacyRepository pharmacyRepository, AppointmentRepository appointmentRepository, DrugQuantityPharmacyRepository drugRepository, DrugReservationRepository reservationRepository, PricelistRepository pricelistRepository) {
		this.pharmacyRepository = pharmacyRepository;
		this.appointmentRepository = appointmentRepository;
		this.drugRepository = drugRepository;
		this.reservationRepository = reservationRepository;
		this.pricelistRepository = pricelistRepository;
	}

	@Override
	public Collection<Map<String, Object>> reportAppointment(int idPharmacy) {
		Collection<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Collection<Doctor> doctors = new ArrayList<Doctor>();
		Pharmacy pharmacy = pharmacyRepository.getOne(idPharmacy);
		for(Appointment a : appointmentRepository.getPerformedAppointmentsInPharamacy(idPharmacy)) {
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

	@Override
	public Collection<Map<String, Object>> reportIncomePharmacy(int idPharmacy, LocalDate start, LocalDate end) {
		Collection<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Pharmacy pharmacy = pharmacyRepository.getOne(idPharmacy);
		double priceDrug = 0;
		double priceAppointment = 0;
		for(DrugReservation d : reservationRepository.findByPharmacyId(idPharmacy)) {
			if(d.isDone() && d.getDateLimit().toLocalDate().isAfter(start) && d.getDateLimit().toLocalDate().isBefore(end)) {
				for(Pricelist p: pricelistRepository.findByDrugId(d.getDrug().getId())) {
					priceDrug+=p.getPrice();
				}
			}
		}
		for(Appointment a: appointmentRepository.getPerformedAppointmentsInPharamacy(idPharmacy)) {
			if(a.getStartTime().toLocalDate().isAfter(start) && a.getStartTime().toLocalDate().isBefore(end)) {
				priceAppointment+=a.getPrice();
			}
		}
			Map<String, Object> item = new HashMap<>();
			item.put("id", 1);
			item.put("pharmacyName", pharmacy.getName());
			item.put("price", priceDrug);
			item.put("priceName", "Drug income");
			result.add(item);
			Map<String, Object> item_app = new HashMap<>();
			item_app.put("id", 2);
			item_app.put("pharmacyName", pharmacy.getName());
			item_app.put("price", priceAppointment);
			item_app.put("priceName", "Medical examination income");
			result.add(item_app);
		
		return result;
	}
	
}

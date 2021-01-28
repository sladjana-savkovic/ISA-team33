package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugReservationRepository;
import rs.ac.uns.ftn.isaproject.repository.users.DoctorRepository;

@Service
public class DrugReservationServiceImpl implements DrugReservationService{
	
	private DrugReservationRepository drugReservationRepository;
	private DoctorRepository doctorRepository;
	
	@Autowired
	public DrugReservationServiceImpl(DrugReservationRepository drugReservationRepository,DoctorRepository doctorRepository) {
		this.drugReservationRepository = drugReservationRepository;
		this.doctorRepository = doctorRepository;
	}

	@Override
	public DrugReservation searchOne(int id, int doctorId){
		List<Pharmacy> doctorPharmacy = new ArrayList<>(doctorRepository.getOne(doctorId).getPharmacies());
		DrugReservation drugReservation = drugReservationRepository.getOne(id);
		if(drugReservation.getDateLimit().isAfter(LocalDateTime.now().minus(Period.ofDays(1))) && doctorPharmacy.get(0).getId() == drugReservation.getPharmacy().getId()) {
				return drugReservation;
		}
		return null;
	}

	@Override
	public void completeReservation(int id) {
		DrugReservation drugReservation = drugReservationRepository.getOne(id);
		drugReservation.setDone(true);
		drugReservationRepository.save(drugReservation);
	}

}

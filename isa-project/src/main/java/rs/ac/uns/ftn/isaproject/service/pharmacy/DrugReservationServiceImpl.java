package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugReservationRepository;

@Service
public class DrugReservationServiceImpl implements DrugReservationService{
	
	private DrugReservationRepository drugReservationRepository;
	
	@Autowired
	public DrugReservationServiceImpl(DrugReservationRepository drugReservationRepository) {
		this.drugReservationRepository = drugReservationRepository;
	}

	@Override
	public DrugReservation getOne(int id) {
		return drugReservationRepository.getOne(id);
	}

}

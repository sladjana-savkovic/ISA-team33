package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugReservationRepository;

@Service
public class DrugReservationServiceImpl implements DrugReservationService{
	
	private DrugReservationRepository drugReservationRepository;
	private DrugQuantityRepository drugQuantityRepository;
	
	@Autowired
	public DrugReservationServiceImpl(DrugReservationRepository drugReservationRepository, DrugQuantityRepository drugQuantityRepository) {
		this.drugReservationRepository = drugReservationRepository;
		this.drugQuantityRepository = drugQuantityRepository;
	}

}

package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.PharmacyActionDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyAction;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyActionRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;

@Service
public class PharmacyActionServiceImpl implements PharmacyActionService{

	private PharmacyActionRepository pharmacyActionRepository;
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	public PharmacyActionServiceImpl(PharmacyActionRepository pharmacyActionRepository, PharmacyRepository pharmacyRepository) {
		this.pharmacyActionRepository = pharmacyActionRepository;
		this.pharmacyRepository = pharmacyRepository;
	}

	@Override
	public void save(PharmacyActionDTO pharmacyActionDTO) {
		
		PharmacyAction pharmacyAction = new PharmacyAction();
		Pharmacy pharmacy = pharmacyRepository.getOne(pharmacyActionDTO.pharmacyId);

		pharmacyAction.setName(pharmacyActionDTO.name);
		pharmacyAction.setDescription(pharmacyActionDTO.description);
		pharmacyAction.setStartDate(pharmacyActionDTO.startDate);
		pharmacyAction.setEndDate(pharmacyActionDTO.endDate);
		pharmacyAction.setPharmacy(pharmacy);

		pharmacyActionRepository.save(pharmacyAction);
	}
}

package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import rs.ac.uns.ftn.isaproject.dto.AddTherapyDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugQuantityPharmacyDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityPharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityPharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugReservationRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;

@Service
public class DrugQuantityPharmacyServiceImpl implements DrugQuantityPharmacyService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private DrugQuantityPharmacyRepository quantityPharmacyRepository;
	private DrugRepository drugRepository;
	private PharmacyRepository pharmacyRepository;
	private DrugReservationRepository drugReservationRepository;
	
	@Autowired
	public DrugQuantityPharmacyServiceImpl(DrugQuantityPharmacyRepository quantityPharmacyRepository, DrugRepository drugRepository, PharmacyRepository pharmacyRepository, DrugReservationRepository drugReservationRepository) {
		this.quantityPharmacyRepository = quantityPharmacyRepository;
		this.drugRepository = drugRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.drugReservationRepository = drugReservationRepository;
	}

	@Override
	public Collection<Drug> findDrugsByPharmacyId(int id) {
		Collection<DrugQuantityPharmacy> drugQuantities = quantityPharmacyRepository.findByPharmacyId(id);
		Collection<Drug> drugs = new ArrayList<Drug>();
		
		for(DrugQuantityPharmacy d: drugQuantities) {
			if(d.isDeleted()==false) {
				drugs.add(drugRepository.getOne(d.getDrug().getId()));
			}
		}
		return drugs;
	}

	@Override
	public boolean checkDrugAvailability(int drugId, int pharmacyId) {
		Collection<DrugQuantityPharmacy> quantityPharmacies = quantityPharmacyRepository.findAll();
		
		for(DrugQuantityPharmacy quantity:quantityPharmacies) {
			if(!quantity.isDeleted() && quantity.getDrug().getId() == drugId && quantity.getPharmacy().getId() == pharmacyId && quantity.getQuantity() > 0)
				return true;
		}
		
		return false;
	}
	
	@Override
	@Transactional(readOnly = false,  propagation = Propagation.REQUIRES_NEW)
	public Collection<DrugQuantityPharmacy> reduceDrugQuantitiesOrReturnMissingDrugs(int pharmacyId, Collection<AddTherapyDTO> therapyDTOs) throws Exception {
		logger.info("> reduceDrugQuantitiesOrReturnMissingDrugs ");
		Collection<DrugQuantityPharmacy> missingDrugIds = new ArrayList<DrugQuantityPharmacy>();
		Collection<DrugQuantityPharmacy> pharmacyQuantities = findByPharmacyId(pharmacyId);
		logger.info("< findByPharmacyId ");
				
		for (AddTherapyDTO therapyDTO:therapyDTOs) 
			for(DrugQuantityPharmacy drugQuantity:pharmacyQuantities) 
				if(drugQuantity.getDrug().getId() == therapyDTO.drugId) {
					if((drugQuantity.getQuantity() > 0))
						drugQuantity.setQuantity(drugQuantity.getQuantity() - 1);
					else
						missingDrugIds.add(drugQuantity);
				}
			
		if(missingDrugIds.size() > 0)  return missingDrugIds;
		
		saveAll(pharmacyQuantities);
		
		logger.info("< reduceDrugQuantitiesOrReturnMissingDrugs ");
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<DrugQuantityPharmacy> findByPharmacyId(int pharmacyId) {
		logger.info("> findByPharmacyId ");
		return quantityPharmacyRepository.findByPharmacyId(pharmacyId);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void saveAll(Collection<DrugQuantityPharmacy> quantityPharmacies) {
		logger.info("> saveAll ");
		quantityPharmacyRepository.saveAll(quantityPharmacies);
		logger.info("< saveAll ");
	}

	@Override
	public Collection<Drug> findAvailableDrugsByPharmacyId(int pharmacyId) {
		Collection<DrugQuantityPharmacy> drugQuantities = quantityPharmacyRepository.findAvailableDrugsByPharmacyId(pharmacyId);
		Collection<Drug> drugs = new ArrayList<Drug>();
		
		for(DrugQuantityPharmacy d: drugQuantities) {
			drugs.add(drugRepository.getOne(d.getDrug().getId()));
		}
		return drugs;
	}

	@Override
	public boolean increaseDrugQuantityPharmacy(int drugId, int pharmacyId, int quantity) {
		Collection<DrugQuantityPharmacy> quantityPharmacies = quantityPharmacyRepository.findAll();
		
		for(DrugQuantityPharmacy drugQuantity:quantityPharmacies) {
			if(drugQuantity.getDrug().getId() == drugId && drugQuantity.getPharmacy().getId() == pharmacyId) {
				drugQuantity.setQuantity(drugQuantity.getQuantity() + quantity);
				quantityPharmacyRepository.save(drugQuantity);
				return true;
			}
		}
		return false;
	}

	@Override
	public void addDrugQuantityPharmacy(DrugQuantityPharmacyDTO drugQuantityDTO) {
		DrugQuantityPharmacy drugQuantity = new DrugQuantityPharmacy();
		Drug drug = drugRepository.getOne(drugQuantityDTO.drugId);
		Pharmacy pharmacy = pharmacyRepository.getOne(drugQuantityDTO.pharmacyId);
		
		drugQuantity.setQuantity(drugQuantityDTO.quantity);
		drugQuantity.setDrug(drug);
		drugQuantity.setPharmacy(pharmacy);
		drugQuantity.setDeleted(false);
		
		quantityPharmacyRepository.save(drugQuantity);
	}

	@Override
	public Collection<DrugDTO> searchByName(String name, Collection<DrugDTO> drugDTOs) {
		Collection<DrugDTO> searchResult = new ArrayList<>();
		
		if(name.equals("&")) name = "";
		
		for(DrugDTO drug:drugDTOs) {
			if(drug.name.toLowerCase().contains(name.toLowerCase())) {
				searchResult.add(drug);
			}
		}
		return searchResult;
	}

	@Override
	public void deleteDrugQuantityPharmacy(int idDrug, int idPharmacy) {
		Collection<DrugQuantityPharmacy> quantityPharmacies = quantityPharmacyRepository.findAll();	
		
		for(DrugQuantityPharmacy drugQuantity:quantityPharmacies) {
			if(drugQuantity.getDrug().getId() == idDrug && drugQuantity.getPharmacy().getId() == idPharmacy && checkIfDrugCanDelete(idDrug, idPharmacy) == true) {
				drugQuantity.setDeleted(true);
				quantityPharmacyRepository.save(drugQuantity);
			}
		}	
	}

	@Override
	public boolean checkIfDrugCanDelete(int idDrug, int idPharmacy) {
		Collection<DrugReservation> drugReservations = drugReservationRepository.findAll();
		for(DrugReservation drugReservation:drugReservations) {
			if(drugReservation.getDrug().getId() == idDrug && drugReservation.getPharmacy().getId() == idPharmacy) {
				if(drugReservation.isDone()==false) {
					return false;
				}
			}
		}	
		return true;
	}

}

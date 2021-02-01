package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

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
			if(quantity.getDrug().getId() == drugId && quantity.getPharmacy().getId() == pharmacyId && quantity.getQuantity() > 0)
				return true;
		}
		
		//TODO: Ako lijek nije dostupan salje se notifikacija adminu apoteke
		return false;
	}

	@Override
	public boolean reduceDrugQuantity(int drugId, int pharmacyId) {
		Collection<DrugQuantityPharmacy> quantityPharmacies = quantityPharmacyRepository.findAll();
		
		for(DrugQuantityPharmacy drugQuantity:quantityPharmacies) {
			if(drugQuantity.getDrug().getId() == drugId && drugQuantity.getPharmacy().getId() == pharmacyId) {
				if(drugQuantity.getQuantity() - 1 > 0) {
					drugQuantity.setQuantity(drugQuantity.getQuantity() - 1);
					quantityPharmacyRepository.save(drugQuantity);
					return true;
				}
			}
		}
		return false;
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
		Collection<DrugReservation> drugReservations = drugReservationRepository.findAll();
		
		boolean can_delete = false;
		for(DrugReservation drugReservation:drugReservations) {
			if(drugReservation.getDrug().getId() == idDrug && drugReservation.getPharmacy().getId() == idPharmacy && drugReservation.isDone()==true) {
				can_delete = true;
			}
		}	
		
		for(DrugQuantityPharmacy drugQuantity:quantityPharmacies) {
			if(drugQuantity.getDrug().getId() == idDrug && drugQuantity.getPharmacy().getId() == idPharmacy && can_delete == true) {
				drugQuantity.setDeleted(true);
				quantityPharmacyRepository.save(drugQuantity);
			}
		}	
	}

}

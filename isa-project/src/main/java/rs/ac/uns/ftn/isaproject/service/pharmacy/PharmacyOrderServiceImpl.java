package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyOrderDTO;
import rs.ac.uns.ftn.isaproject.model.enums.PurposeOfDrugQuantity;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantity;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pricelist;
import rs.ac.uns.ftn.isaproject.model.users.PharmacyAdministrator;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyOrderRepository;
import rs.ac.uns.ftn.isaproject.repository.users.PharmacyAdministratorRepository;

@Service
public class PharmacyOrderServiceImpl implements PharmacyOrderService{

	private PharmacyOrderRepository pharmacyOrderRepository;
	private DrugQuantityRepository drugQuantityRepository;
	private Set<DrugQuantity> drugQuantities;
	private DrugRepository drugRepository;
	private PharmacyAdministratorRepository pharmacyAdministratorRepository;
	
	@Autowired
	public PharmacyOrderServiceImpl(PharmacyOrderRepository pharmacyOrderRepository, DrugQuantityRepository drugQuantityRepository, DrugRepository drugRepository, PharmacyAdministratorRepository pharmacyAdministratorRepository) {
		this.pharmacyOrderRepository = pharmacyOrderRepository;
		this.drugQuantityRepository = drugQuantityRepository;
		this.drugQuantities = new HashSet<DrugQuantity>();
		this.drugRepository = drugRepository;
		this.pharmacyAdministratorRepository = pharmacyAdministratorRepository;
	}

	@Override
	public PharmacyOrder findById(int id) {
		return pharmacyOrderRepository.getOne(id);
	}

	@Override
	public void addDrugQuantity(DrugQuantityDTO drugQuantityDTO) {
		DrugQuantity drugQuantity = new DrugQuantity();
		Drug drug = drugRepository.getOne(drugQuantityDTO.idDrug);
		
		
		drugQuantity.setQuantity(drugQuantityDTO.quantity);
		drugQuantity.setDrug(drug);
		drugQuantity.setPurpose(PurposeOfDrugQuantity.Order);
		drugQuantities.add(drugQuantity);
		
		drugQuantityRepository.save(drugQuantity);
		
	}

	@Override
	public void add(PharmacyOrderDTO pharmacyOrderDTO) {
		PharmacyOrder pharmacyOrder = new PharmacyOrder();
		PharmacyAdministrator pharmacyAdministrator = pharmacyAdministratorRepository.getOne(pharmacyOrderDTO.idPharmacyAdmn);
		
		pharmacyOrder.setFinished(false);
		pharmacyOrder.setLimitDate(pharmacyOrderDTO.limitDate);
		pharmacyOrder.setPharmacyAdministrator(pharmacyAdministrator);
		
		drugQuantities = new HashSet<DrugQuantity>();
		DrugQuantity dg = new DrugQuantity();
		Drug drug = drugRepository.getOne(1);
		dg.setDrug(drug);
		dg.setId(6);
		dg.setQuantity(88);
		dg.setPurpose(PurposeOfDrugQuantity.Order);
		drugQuantities.add(dg);
		
		pharmacyOrder.setOrderedDrugs(drugQuantities);
		
		pharmacyOrderRepository.save(pharmacyOrder);
		
	}
}
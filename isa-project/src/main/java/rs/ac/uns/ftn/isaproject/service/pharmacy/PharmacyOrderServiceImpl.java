package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityOrderDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyOrderDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;
import rs.ac.uns.ftn.isaproject.model.users.PharmacyAdministrator;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantityOrderRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyOrderRepository;
import rs.ac.uns.ftn.isaproject.repository.users.PharmacyAdministratorRepository;

@Service
public class PharmacyOrderServiceImpl implements PharmacyOrderService{

	private PharmacyOrderRepository pharmacyOrderRepository;
	private DrugQuantityOrderRepository drugQuantityRepository;
	private DrugRepository drugRepository;
	private PharmacyAdministratorRepository pharmacyAdministratorRepository;
	
	@Autowired
	public PharmacyOrderServiceImpl(PharmacyOrderRepository pharmacyOrderRepository, DrugQuantityOrderRepository drugQuantityRepository, DrugRepository drugRepository, PharmacyAdministratorRepository pharmacyAdministratorRepository) {
		this.pharmacyOrderRepository = pharmacyOrderRepository;
		this.drugQuantityRepository = drugQuantityRepository;
		this.drugRepository = drugRepository;
		this.pharmacyAdministratorRepository = pharmacyAdministratorRepository;
	}

	@Override
	public PharmacyOrder findById(int id) {
		return pharmacyOrderRepository.getOne(id);
	}

	@Override
	public void addDrugQuantity(DrugQuantityOrderDTO drugQuantityDTO) {
		DrugQuantityOrder drugQuantity = new DrugQuantityOrder();
		Drug drug = drugRepository.getOne(drugQuantityDTO.idDrug);
		PharmacyOrder pharmacyOrder = pharmacyOrderRepository.getOne(drugQuantityDTO.idPharmacyOrder);
		
		drugQuantity.setQuantity(drugQuantityDTO.quantity);
		drugQuantity.setDrug(drug);
		drugQuantity.setPharmacyOrder(pharmacyOrder);
		
		drugQuantityRepository.save(drugQuantity);
		
	}

	@Override
	public void add(PharmacyOrderDTO pharmacyOrderDTO) {
		PharmacyOrder pharmacyOrder = new PharmacyOrder();
		PharmacyAdministrator pharmacyAdministrator = pharmacyAdministratorRepository.getOne(pharmacyOrderDTO.idPharmacyAdmn);
		
		pharmacyOrder.setFinished(false);
		pharmacyOrder.setLimitDate(pharmacyOrderDTO.limitDate);
		pharmacyOrder.setPharmacyAdministrator(pharmacyAdministrator);
		
		pharmacyOrderRepository.save(pharmacyOrder);
		
	}

	@Override
	public int findByMaxId() {
		return pharmacyOrderRepository.findByMaxId();
	}

	@Override
	public Collection<DrugQuantityOrder> findByPharmacyOrderId(int id) {
		return drugQuantityRepository.findByPharmacyOrderId(id);
	}

	@Override
	public Collection<PharmacyOrder> findByPharmacyId(int id) {
		return pharmacyOrderRepository.findByPharmacyId(id);
	}
	
	@Override
	public Collection<PharmacyOrder> findAll() {
		return pharmacyOrderRepository.findAll();		
	}		
	
}
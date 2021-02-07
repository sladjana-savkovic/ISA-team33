package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityOrderDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyOrderDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;
import rs.ac.uns.ftn.isaproject.model.users.PharmacyAdministrator;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugOfferRepository;
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
	private DrugOfferRepository drugOfferRepository;
	
	@Autowired
	public PharmacyOrderServiceImpl(PharmacyOrderRepository pharmacyOrderRepository, DrugQuantityOrderRepository drugQuantityRepository, DrugRepository drugRepository, PharmacyAdministratorRepository pharmacyAdministratorRepository, DrugOfferRepository drugOfferRepository) {
		this.pharmacyOrderRepository = pharmacyOrderRepository;
		this.drugQuantityRepository = drugQuantityRepository;
		this.drugRepository = drugRepository;
		this.pharmacyAdministratorRepository = pharmacyAdministratorRepository;
		this.drugOfferRepository = drugOfferRepository;
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

	@Override
	public boolean delete(int id) {
		PharmacyOrder pharmacyOrder = pharmacyOrderRepository.getOne(id);
		Collection<DrugOffer> drugOffers = drugOfferRepository.findAll();
		for(DrugOffer d : drugOffers) {
			if(d.getPharmacyOrder().getId() == pharmacyOrder.getId()) {
				return false;
			}
		}
		pharmacyOrder.setDeleted(true);
		pharmacyOrderRepository.save(pharmacyOrder);
		return true;
		
	}

	@Override
	public boolean edit(int id, LocalDate limitDate) {
		PharmacyOrder pharmacyOrder = pharmacyOrderRepository.getOne(id);
		if(checkOrderHasOffer(id)==true) {
			return false;
		}
		pharmacyOrder.setLimitDate(limitDate);
		pharmacyOrderRepository.save(pharmacyOrder);
		return true;
	}

	@Override
	public Collection<PharmacyOrderDTO> filterByFinish(boolean isFinished, Collection<PharmacyOrderDTO> pharmacyOrderDTOs) {
		Collection<PharmacyOrderDTO> searchResult = new ArrayList<>();
		for(PharmacyOrderDTO order:pharmacyOrderDTOs) {
			if(order.isFinished == isFinished) {
				searchResult.add(order);
			}
		}
		return searchResult;
	}

	@Override
	public boolean checkOrderHasOffer(int id) {
		PharmacyOrder pharmacyOrder = pharmacyOrderRepository.getOne(id);
		Collection<DrugOffer> drugOffers = drugOfferRepository.findAll();
		for(DrugOffer d : drugOffers) {
			if(d.getPharmacyOrder().getId() == pharmacyOrder.getId()) {
				return true;
			}
		}
		return false;
	}		
	
}
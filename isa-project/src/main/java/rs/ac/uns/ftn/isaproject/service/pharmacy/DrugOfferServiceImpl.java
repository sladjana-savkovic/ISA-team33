package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddDrugOfferDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugOfferAndOrderDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugOfferSearchDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugQuantityOrderMapper;
import rs.ac.uns.ftn.isaproject.model.enums.OfferStatus;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantitySupplier;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugOfferRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantitySupplierRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyOrderRepository;
import rs.ac.uns.ftn.isaproject.repository.users.SupplierRepository;
import rs.ac.uns.ftn.isaproject.repository.users.UserAccountRepository;

@Service
public class DrugOfferServiceImpl implements DrugOfferService{
	
	private DrugOfferRepository drugOfferRepository;
	private PharmacyOrderRepository pharmacyOrderRepository;
	private DrugQuantitySupplierRepository drugQuantitySupplierRepository;
	private SupplierRepository supplierRepository;
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	public DrugOfferServiceImpl(DrugOfferRepository drugOfferRepository, PharmacyOrderRepository pharmacyOrderRepository, 
								DrugQuantitySupplierRepository drugQuantitySupplierRepository, SupplierRepository supplierRepository,
								UserAccountRepository userAccountRepository) {
		this.drugOfferRepository = drugOfferRepository;
		this.pharmacyOrderRepository = pharmacyOrderRepository;
		this.drugQuantitySupplierRepository = drugQuantitySupplierRepository;
		this.supplierRepository = supplierRepository;
		this.userAccountRepository = userAccountRepository;
	}

	@Override
	public void acceptOffer(int id) throws Exception {
		DrugOffer drugOffer = drugOfferRepository.getOne(id);
		PharmacyOrder pharmacyOrder = pharmacyOrderRepository.getOne(drugOffer.getPharmacyOrder().getId());
		
	    if (pharmacyOrder.getDrugQuantityOrders().size() > drugQuantitySupplierRepository.getNumberOfAvailableDrugs(drugOffer.getSupplier().getId(), pharmacyOrder.getId()) ) {
	    	throw new Exception("The offer was not successfully accepted. The supplier does not have enough drugs ");	    	
	    }	
		
		if(pharmacyOrder.getLimitDate().isBefore(LocalDate.now())) {
			drugOffer.setStatus(OfferStatus.Accepted);
			drugOfferRepository.save(drugOffer);
			updateDrugStorageSupplier(pharmacyOrder.getDrugQuantityOrders(), drugOffer.getSupplier().getId());
		}		
	}

	@Override
	public Collection<DrugOffer> findByPharmacyOrderId(int id) {
		return drugOfferRepository.findByPharmacyOrderId(id);
	}

	@Override
	public Collection<DrugOffer> findByPharmacyId(int id) {
		return drugOfferRepository.findByPharmacyId(id);
	}

	@Override
	public void rejectOffer(int id) {
		DrugOffer drugOffer = drugOfferRepository.getOne(id);
		drugOffer.setStatus(OfferStatus.Rejected);
		drugOfferRepository.save(drugOffer);
	}

	@Override
	public DrugOffer findById(int id) {
		return drugOfferRepository.getOne(id);
	}

	@Override
	public Supplier findSupplierById(int id) {
		return drugOfferRepository.findSupplierById(id);
	}

	@Override
	public void add(AddDrugOfferDTO drugOfferDTO) throws Exception {
	    PharmacyOrder pharmacyOrder = pharmacyOrderRepository.getOne(drugOfferDTO.orderId);
	    
	    if (drugOfferDTO.limitDate.isAfter(pharmacyOrder.getLimitDate())) {	    	
	    	 throw new Exception("The delivery date is not valid.");
	    }	    
	    if (pharmacyOrder.getDrugQuantityOrders().size() > drugQuantitySupplierRepository.getNumberOfAvailableDrugs(drugOfferDTO.supplierId, drugOfferDTO.orderId) ) {
	    	throw new Exception("No drugs available.");	    	
	    }	 
	    
	    Supplier supplier = supplierRepository.getOne(drugOfferDTO.supplierId);	
	    DrugOffer drugOffer = drugOfferRepository.findOfferIdBySupplierAndOrder(drugOfferDTO.supplierId, drugOfferDTO.orderId);
	    	    
	    if (drugOffer == null) {
	    	drugOffer = new DrugOffer();	    		    	
	    }
	    else if (drugOffer.getStatus().equals(OfferStatus.Rejected)) {
	    	drugOffer = new DrugOffer();
	    }
	    else if (drugOffer.getStatus().equals(OfferStatus.Accepted)) {
	    	throw new Exception("The offer has already been accepted.");	
	    }	    
	    drugOffer.setPharmacyOrder(pharmacyOrder);	    	
    	drugOffer.setSupplier(supplier);
	    drugOffer.setLimitDate(drugOfferDTO.limitDate);	    
	    drugOffer.setTotalPrice(drugOfferDTO.totalPrice);
	    drugOffer.setStatus(OfferStatus.Waiting);
	    	    
	    drugOfferRepository.save(drugOffer);
	}

	@Override
	public Collection<DrugOffer> findAllBySupplierId(int id) {
		return drugOfferRepository.findAllBySupplierId(id);
	}

	@Override
	public Collection<DrugOfferAndOrderDTO> searchByStatus(DrugOfferSearchDTO offerSearchDTO) {
		Collection<DrugOfferAndOrderDTO> searchResult = new ArrayList<>();	
		if (offerSearchDTO.status.equals(""))
			return offerSearchDTO.offerDTOs;
		for(DrugOfferAndOrderDTO dto : offerSearchDTO.offerDTOs) {			
			if(OfferStatus.valueOf(offerSearchDTO.status).equals(dto.drugOfferDTO.status)) {
				searchResult.add(dto);
			}
		}
		return searchResult;		
	}

	
	
	private void updateDrugStorageSupplier(Collection<DrugQuantityOrder> drugQuantityOrders, int supplierId) {
		Collection<DrugQuantitySupplier> drugQuantitiesSupplier = drugQuantitySupplierRepository.findBySupplierId(supplierId);
		for (DrugQuantitySupplier drugQuantitySupplier : drugQuantitiesSupplier) {
			for (DrugQuantityOrder drugQuantityOrder : drugQuantityOrders) {
				if (drugQuantityOrder.getDrug().getId() == drugQuantitySupplier.getDrug().getId()) {
					int quantity = drugQuantitySupplier.getQuantity() - drugQuantityOrder.getQuantity();
					drugQuantitySupplier.setQuantity(quantity);
					drugQuantitySupplierRepository.save(drugQuantitySupplier);
				}
			}			
		}		
	}
	
	
}

package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.AddDrugOfferDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugOfferAndOrderDTO;
import rs.ac.uns.ftn.isaproject.dto.DrugOfferSearchDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugOffer;
import rs.ac.uns.ftn.isaproject.model.users.Supplier;

public interface DrugOfferService {
	
	void acceptOffer(int id);
	void rejectOffer(int id);
	Collection<DrugOffer> findByPharmacyOrderId(int id);
	Collection<DrugOffer> findByPharmacyId(int id);
	Collection<DrugOffer> findAllBySupplierId(int id);
	DrugOffer findById(int id);
	Supplier findSupplierById(int id);
	void add(AddDrugOfferDTO drugOfferDTO) throws Exception;
	Collection<DrugOfferAndOrderDTO> searchByStatus(DrugOfferSearchDTO offerSearchDTO);
}

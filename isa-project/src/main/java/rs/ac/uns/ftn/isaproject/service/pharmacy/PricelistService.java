package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.PricelistDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pricelist;

public interface PricelistService {

	void save(PricelistDTO pricelistDTO);
	Collection<Pricelist> getPharmaciesAndPriceByDrugId(int drugId);
	Collection<Pricelist> getPricelistByPharmacy(int pharmacyId);
}

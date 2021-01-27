package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;

import rs.ac.uns.ftn.isaproject.repository.pharmacy.PricelistRepository;

public class PricelistServiceImpl implements PricelistService {

	private PricelistRepository pricelistRepository;
	
	@Autowired
	public PricelistServiceImpl(PricelistRepository pricelistRepository) {
		this.pricelistRepository = pricelistRepository;
	}
}
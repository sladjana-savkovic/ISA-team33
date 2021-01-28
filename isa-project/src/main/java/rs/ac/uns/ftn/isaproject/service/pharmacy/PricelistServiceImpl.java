package rs.ac.uns.ftn.isaproject.service.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.repository.pharmacy.PricelistRepository;

@Service
public class PricelistServiceImpl implements PricelistService {

	private PricelistRepository pricelistRepository;
	
	@Autowired
	public PricelistServiceImpl(PricelistRepository pricelistRepository) {
		this.pricelistRepository = pricelistRepository;
	}
}
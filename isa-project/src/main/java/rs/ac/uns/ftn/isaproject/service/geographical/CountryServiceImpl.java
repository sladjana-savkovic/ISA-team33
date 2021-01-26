package rs.ac.uns.ftn.isaproject.service.geographical;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isaproject.model.geographical.Country;
import rs.ac.uns.ftn.isaproject.repository.geographical.CountryRepository;


@Service
public class CountryServiceImpl implements CountryService{

	private CountryRepository countryRepository;
	
	@Autowired
	public CountryServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public Collection<Country> findAll() {
		return countryRepository.findAll();
	}
}

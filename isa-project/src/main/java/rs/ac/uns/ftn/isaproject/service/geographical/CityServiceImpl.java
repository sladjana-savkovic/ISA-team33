package rs.ac.uns.ftn.isaproject.service.geographical;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.geographical.City;
import rs.ac.uns.ftn.isaproject.repository.geographical.CityRepository;

@Service
public class CityServiceImpl implements CityService {

	private CityRepository cityRepository;
	
	@Autowired
	public CityServiceImpl(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@Override
	public Collection<City> findAllByCountryId(int countryId) {
		return cityRepository.findAllByCountryId(countryId);
	}
	
}

package rs.ac.uns.ftn.isaproject.service.geographical;

import java.util.Collection;
import rs.ac.uns.ftn.isaproject.model.geographical.City;

public interface CityService {

	Collection<City> findAllByCountryId(int countryId);
}

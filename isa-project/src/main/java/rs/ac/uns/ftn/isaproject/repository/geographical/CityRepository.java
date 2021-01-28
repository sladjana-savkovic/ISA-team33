package rs.ac.uns.ftn.isaproject.repository.geographical;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.geographical.City;

public interface CityRepository extends JpaRepository<City, Integer> {
	
	@Query(value = "select * from City c where c.country_id = ?1", nativeQuery = true)
	Collection<City> findAllByCountryId(int countryId);
}

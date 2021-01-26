package rs.ac.uns.ftn.isaproject.repository.geographical;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.isaproject.model.geographical.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {

}

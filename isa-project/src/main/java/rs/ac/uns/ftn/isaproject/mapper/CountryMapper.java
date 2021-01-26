package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.geographical.CountryDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.Country;

public class CountryMapper {

	
	public static Collection<CountryDTO> toCountryDTOs(Collection<Country> countries){
		
		Collection<CountryDTO> countryDTOs = new ArrayList<>();
		
		for(Country c:countries) {
			countryDTOs.add(new CountryDTO(c.getId(), c.getName()));
		}
		
		return countryDTOs;
	}
}

package rs.ac.uns.ftn.isaproject.mapper;

import java.util.ArrayList;
import java.util.Collection;

import rs.ac.uns.ftn.isaproject.dto.CityDTO;
import rs.ac.uns.ftn.isaproject.model.geographical.City;

public class CityMapper {

	public static Collection<CityDTO> toCityDTOs(Collection<City> cities){
		Collection<CityDTO> cityDTOs = new ArrayList<>();
		for(City city:cities) {
			cityDTOs.add(new CityDTO(city.getId(), city.getName()));
		}
		return cityDTOs;
	}
}

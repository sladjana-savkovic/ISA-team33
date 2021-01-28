package rs.ac.uns.ftn.isaproject.controller.geographical;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.CityDTO;
import rs.ac.uns.ftn.isaproject.mapper.CityMapper;
import rs.ac.uns.ftn.isaproject.service.geographical.CityService;

@RestController
@RequestMapping(value = "api/city")
public class CityController {

	private CityService cityService;
	
	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping("/country/{id}")
	public ResponseEntity<Collection<CityDTO>> findAllByCountryId(@PathVariable int id){
		Collection<CityDTO> cityDTOs = CityMapper.toCityDTOs(cityService.findAllByCountryId(id));
		return new ResponseEntity<Collection<CityDTO>>(cityDTOs,HttpStatus.OK);
	}
}

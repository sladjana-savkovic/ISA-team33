package rs.ac.uns.ftn.isaproject.controller.geographical;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.CountryDTO;
import rs.ac.uns.ftn.isaproject.mapper.CountryMapper;
import rs.ac.uns.ftn.isaproject.service.geographical.CountryService;


@RestController
@RequestMapping(value = "api/country")
public class CountryController {
	
	private CountryService countryService;
	
	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}
	
	@GetMapping
	public ResponseEntity<Collection<CountryDTO>> findAll(){
		Collection<CountryDTO> countries = CountryMapper.toCountryDTOs(countryService.findAll());
		return new ResponseEntity<>(countries, HttpStatus.OK);
	}
	
}

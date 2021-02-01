package rs.ac.uns.ftn.isaproject.controller.users;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isaproject.dto.AddVacationRequestDTO;
import rs.ac.uns.ftn.isaproject.dto.ViewVacationRequestDTO;
import rs.ac.uns.ftn.isaproject.mapper.VacationRequestMapper;
import rs.ac.uns.ftn.isaproject.model.enums.VacationRequestStatus;
import rs.ac.uns.ftn.isaproject.model.users.VacationRequest;
import rs.ac.uns.ftn.isaproject.service.users.VacationRequestService;

@RestController
@RequestMapping(value = "api/vacation")
public class VacationRequestController {

	private VacationRequestService vacationService;
	
	@Autowired
	public VacationRequestController(VacationRequestService vacationService) {
		this.vacationService = vacationService;
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Void> add(@RequestBody AddVacationRequestDTO vacationRequestDTO){
		try {
			vacationService.add(vacationRequestDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> acceptRequest(@PathVariable int id){
		vacationService.acceptRequest(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/{id}/reason/{value}")
	public ResponseEntity<Void> rejectRequest(@PathVariable int id, @PathVariable String value){
		vacationService.rejectRequest(id, value);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/pharmacy/{id}")
	public ResponseEntity<Collection<ViewVacationRequestDTO>> findByPharmacyId(@PathVariable int id){
		Collection<VacationRequest> requests = vacationService.findByPharmacyId(id);
		Collection<VacationRequest> createdRequests = new ArrayList<VacationRequest>();
		for(VacationRequest v: requests) {
			if(v.getStatus().equals(VacationRequestStatus.Created)) {
				createdRequests.add(v);
			}
		}
		Collection<ViewVacationRequestDTO> vacationRequestDTOs = VacationRequestMapper.toViewVacationRequestDTOs(createdRequests);
		return new ResponseEntity<Collection<ViewVacationRequestDTO>>(vacationRequestDTOs, HttpStatus.OK);
	}
	
}

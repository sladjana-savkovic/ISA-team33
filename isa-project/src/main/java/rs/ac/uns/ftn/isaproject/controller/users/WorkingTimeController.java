package rs.ac.uns.ftn.isaproject.controller.users;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.ViewWorkingTimeDTO;
import rs.ac.uns.ftn.isaproject.dto.WorkingTimeDTO;
import rs.ac.uns.ftn.isaproject.mapper.ViewWorkingTimeMapper;
import rs.ac.uns.ftn.isaproject.service.users.WorkingTimeService;

@RestController
@RequestMapping(value = "api/working-time")
public class WorkingTimeController {

	private WorkingTimeService workingTimeService;
	
	@Autowired
	public WorkingTimeController(WorkingTimeService workingTimeService) {
		this.workingTimeService = workingTimeService;
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> add(@RequestBody WorkingTimeDTO workingTimeDTO){
		try {
			workingTimeService.add(workingTimeDTO);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while create doctor's working time.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/pharmacy")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> findByPharmacyId(@PathVariable int id) {
		try {
			Collection<ViewWorkingTimeDTO> workingTimeDTOs = ViewWorkingTimeMapper.toViewWorkingTimeDTOs(workingTimeService.findByPharmacyId(id));
			return new ResponseEntity<Collection<ViewWorkingTimeDTO>>(workingTimeDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting doctor's working time.", HttpStatus.BAD_REQUEST);
		}
	}
	
}

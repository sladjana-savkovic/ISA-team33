package rs.ac.uns.ftn.isaproject.controller.pharmacy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isaproject.dto.DrugQuantityOrderDTO;
import rs.ac.uns.ftn.isaproject.dto.OrderAndQuantityDTO;
import rs.ac.uns.ftn.isaproject.dto.PharmacyOrderDTO;
import rs.ac.uns.ftn.isaproject.mapper.DrugQuantityOrderMapper;
import rs.ac.uns.ftn.isaproject.mapper.OrderAndQuantityMapper;
import rs.ac.uns.ftn.isaproject.mapper.PharmacyOrderMapper;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;
import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.service.pharmacy.PharmacyOrderService;

@RestController
@RequestMapping(value = "api/order")
public class PharmacyOrderController {
	
	private PharmacyOrderService pharmacyOrderService;
	
	@Autowired
	public PharmacyOrderController(PharmacyOrderService pharmacyOrderService) {
		this.pharmacyOrderService = pharmacyOrderService;
	}
	
	@RequestMapping(path = "/drug-quantity", method = RequestMethod.POST, consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> addDrugQuantity(@RequestBody DrugQuantityOrderDTO drugQuantityDTO) {
		try {
			pharmacyOrderService.addDrugQuantity(drugQuantityDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while adding drug quantity.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> add(@RequestBody PharmacyOrderDTO pharmacyOrderDTO) {
		try {
			UserAccount u = (UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pharmacyOrderDTO.idPharmacyAdmn = u.getUser().getId();
			pharmacyOrderService.add(pharmacyOrderDTO);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating pharmacy order.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/last")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> findByMaxId(){
		try {
			return new ResponseEntity<>(pharmacyOrderService.findByMaxId(), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while creating pharmacy order.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> getById(@PathVariable int id) {
		try {
			PharmacyOrder pharmacyOrder = pharmacyOrderService.findById(id);

			if (pharmacyOrder == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			PharmacyOrderDTO pharmacyOrderDTO = new PharmacyOrderDTO(pharmacyOrder.getId(), pharmacyOrder.getLimitDate(), pharmacyOrder.isFinished(), pharmacyOrder.getPharmacyAdministrator().getId(), pharmacyOrder.isDeleted());
			return new ResponseEntity<>(pharmacyOrderDTO, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting pharmacy order.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}/drug-quantity")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> getDrugQuantityByPharmacyOrderId(@PathVariable int id) {
		try {	
			Collection<DrugQuantityOrder> drugs = pharmacyOrderService.findByPharmacyOrderId(id);

			if (drugs == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		
			Collection<DrugQuantityOrderDTO> drugDTOs = DrugQuantityOrderMapper.toDrugQuantityOrderDTOs(drugs);
			
			return new ResponseEntity<>(drugDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting quantity in pharmacy order.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}/pharmacy")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> getByPharmacyId(@PathVariable int id) {
		try {
			Collection<PharmacyOrder> pharmacyOrders = pharmacyOrderService.findByPharmacyId(id);

			if (pharmacyOrders == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			Collection<PharmacyOrderDTO> pharmacyOrderDTOs = PharmacyOrderMapper.toPharmacyOrderDTOs(pharmacyOrders);
			return new ResponseEntity<>(pharmacyOrderDTOs, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while getting pharmacy order.", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_SUPPLIER')")
	public ResponseEntity<Collection<OrderAndQuantityDTO>> getAllPharmacyOrders() {
		try {
			Collection<PharmacyOrder> pharmacyOrders = pharmacyOrderService.findAll();			
			if (pharmacyOrders == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			Collection<OrderAndQuantityDTO> orderAndQuantityDTOs = OrderAndQuantityMapper.toOrderAndQuantityDTOs(pharmacyOrders);
			return new ResponseEntity<>(orderAndQuantityDTOs, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@PutMapping("/{id}/delete")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> deleteOrder(@PathVariable int id){
		try {
			pharmacyOrderService.delete(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while deleting pharmacy order.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}/{date}/edit")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> editOrder(@PathVariable int id, @PathVariable String date){
		try {
			LocalDate parse_date = LocalDate.parse(date);
			pharmacyOrderService.edit(id, parse_date);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while editing pharmacy order.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/filter/{finish}")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> filterByFinish(@PathVariable boolean finish, @RequestBody ArrayList<PharmacyOrderDTO> orderDTOs){
		try {
			Collection<PharmacyOrderDTO> searchResult = pharmacyOrderService.filterByFinish(finish, orderDTOs);
			return new ResponseEntity<Collection<PharmacyOrderDTO>>(searchResult, HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while filtering pharmacy order.", HttpStatus.BAD_REQUEST);
		}
		}
	
	@GetMapping("/{id}/check-offer")
	@PreAuthorize("hasRole('ROLE_PHARMACYADMIN')")
	public ResponseEntity<?> checkOffer(@PathVariable int id){
		try {
			return new ResponseEntity<Boolean>(pharmacyOrderService.checkOrderHasOffer(id), HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while checking pharmacy order.", HttpStatus.BAD_REQUEST);
		}
	}
	
}


package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;
import java.util.Collection;

public class OrderAndQuantityDTO {

	public int id;
	public LocalDate limitDate;
	public boolean isFinished;
	public Collection<DrugQuantityOrderDTO> drugQuantityDTOs; 
	
	public OrderAndQuantityDTO() { }
	
}

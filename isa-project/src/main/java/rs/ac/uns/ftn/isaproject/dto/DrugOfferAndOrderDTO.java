package rs.ac.uns.ftn.isaproject.dto;

public class DrugOfferAndOrderDTO {

	public DrugOfferDTO drugOfferDTO;
	public OrderAndQuantityDTO orderAndQuantityDTO;
	
	public DrugOfferAndOrderDTO() { }

	public DrugOfferAndOrderDTO(DrugOfferDTO drugOfferDTO, OrderAndQuantityDTO orderAndQuantityDTO) {
		super();
		this.drugOfferDTO = drugOfferDTO;
		this.orderAndQuantityDTO = orderAndQuantityDTO;
	}
	
}

package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class DrugOfferDTO {

	public int id;
	public double totalPrice;
	public boolean isAccepted;
	public LocalDate limitDate;
	public int pharmacyOrderId;
	
	public DrugOfferDTO() {}
	
	public DrugOfferDTO(int id, double totalPrice, boolean isAccepted, LocalDate limitDate, int pharmacyOrderId) {
		super();
		this.id = id;
		this.totalPrice = totalPrice;
		this.isAccepted = isAccepted;
		this.limitDate = limitDate;
		this.pharmacyOrderId = pharmacyOrderId;
	}
	
}

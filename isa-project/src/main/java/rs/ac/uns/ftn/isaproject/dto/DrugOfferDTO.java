package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

import rs.ac.uns.ftn.isaproject.model.enums.OfferStatus;

public class DrugOfferDTO {

	public int id;
	public double totalPrice;
	public OfferStatus status;
	public LocalDate limitDate;
	public int pharmacyOrderId;
	public int supplierId;
	
	public DrugOfferDTO() {}
	
	public DrugOfferDTO(int id, double totalPrice, OfferStatus status, LocalDate limitDate, int pharmacyOrderId, int supplierId) {
		super();
		this.id = id;
		this.totalPrice = totalPrice;
		this.status = status;
		this.limitDate = limitDate;
		this.pharmacyOrderId = pharmacyOrderId;
		this.supplierId = supplierId;
	}
	
}

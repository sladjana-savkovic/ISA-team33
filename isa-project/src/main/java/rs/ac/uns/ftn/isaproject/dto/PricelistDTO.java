package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class PricelistDTO {

	public int id;
	public LocalDate startDate;
	public LocalDate endDate;
	public double price;
	public int idPharmacy;
	public int idDrug;
	
	public PricelistDTO() {}
	
	public PricelistDTO(int id, LocalDate startDate, LocalDate endDate, double price, int idPharmacy, int idDrug) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.idPharmacy = idPharmacy;
		this.idDrug = idDrug;
	}
	
}

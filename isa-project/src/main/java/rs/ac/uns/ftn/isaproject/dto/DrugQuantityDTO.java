package rs.ac.uns.ftn.isaproject.dto;

public class DrugQuantityDTO {

	public int id;
	public int idDrug;
	public int quantity;
	public int idPharmacyOrder;
	
	public DrugQuantityDTO() {}
	
	public DrugQuantityDTO(int id, int idDrug, int quantity, int idPharmacyOrder) {
		super();
		this.id = id;
		this.idDrug = idDrug;
		this.quantity = quantity;
		this.idPharmacyOrder = idPharmacyOrder;
	}
	
}

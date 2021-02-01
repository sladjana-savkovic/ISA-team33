package rs.ac.uns.ftn.isaproject.dto;

public class DrugQuantityOrderDTO {

	public int id;
	public int idDrug;
	public String drugName;
	public int quantity;
	public int idPharmacyOrder;
	
	public DrugQuantityOrderDTO() {}
	
	public DrugQuantityOrderDTO(int id, int idDrug, int quantity, int idPharmacyOrder, String drugName) {
		super();
		this.id = id;
		this.idDrug = idDrug;
		this.quantity = quantity;
		this.idPharmacyOrder = idPharmacyOrder;
		this.drugName = drugName;
	}
	
}

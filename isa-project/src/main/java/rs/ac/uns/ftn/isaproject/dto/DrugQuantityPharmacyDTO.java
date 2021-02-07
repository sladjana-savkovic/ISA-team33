package rs.ac.uns.ftn.isaproject.dto;

public class DrugQuantityPharmacyDTO {
	public int id;
	public int drugId;
	public String drugName;
	public int pharmacyId;
	public int quantity;
	
	public DrugQuantityPharmacyDTO() {}
	
	public DrugQuantityPharmacyDTO(int id, int drugId, int pharmacyId, int quantity) {
		super();
		this.id = id;
		this.drugId = drugId;
		this.pharmacyId = pharmacyId;
		this.quantity = quantity;
	}

	public DrugQuantityPharmacyDTO(int drugId, String drugName) {
		super();
		this.drugId = drugId;
		this.drugName = drugName;
	}
}

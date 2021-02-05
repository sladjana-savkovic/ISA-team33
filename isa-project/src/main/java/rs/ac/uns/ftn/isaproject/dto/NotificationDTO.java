package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDateTime;

public class NotificationDTO {

	public int id;
	public int drugId;
	public int pharmacyId;
	public String drugName;
	public String typeOfDrug;
	public String typeOfDrugForm;
	public LocalDateTime date;
	
	public NotificationDTO() {}

	public NotificationDTO(int drugId, int pharmacyId) {
		super();
		this.drugId = drugId;
		this.pharmacyId = pharmacyId;
	}
	
	public NotificationDTO(int id, int drugId, int pharmacyId, String drugName, String typeOfDrug, String typeOfDrugForm,LocalDateTime date) {
		super();
		this.id = id;
		this.drugId = drugId;
		this.pharmacyId = pharmacyId;
		this.drugName = drugName;
		this.typeOfDrug = typeOfDrug;
		this.typeOfDrugForm = typeOfDrugForm;
		this.date = date;
	}
}

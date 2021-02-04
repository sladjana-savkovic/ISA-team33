package rs.ac.uns.ftn.isaproject.dto;

public class NotificationDTO {

	public int drugId;
	public int pharmacyId;
	
	public NotificationDTO() {}

	public NotificationDTO(int drugId, int pharmacyId) {
		super();
		this.drugId = drugId;
		this.pharmacyId = pharmacyId;
	}
	
}

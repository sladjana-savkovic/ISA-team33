package rs.ac.uns.ftn.isaproject.dto;

public class DoctorPharmacyDTO {

	public int doctorId;
	public int pharmacyId;
	public String pharmacyName;
	
	public DoctorPharmacyDTO() {}
	
	public DoctorPharmacyDTO(int doctorId, int pharmacyId, String pharmacyName) {
		super();
		this.doctorId = doctorId;
		this.pharmacyId = pharmacyId;
		this.pharmacyName = pharmacyName;
	}
	
}

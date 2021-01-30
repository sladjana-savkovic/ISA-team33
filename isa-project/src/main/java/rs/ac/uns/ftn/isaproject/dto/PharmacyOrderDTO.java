package rs.ac.uns.ftn.isaproject.dto;

import java.time.LocalDate;

public class PharmacyOrderDTO {

	public int id;
	public LocalDate limitDate;
	public boolean isFinished;
	public int idPharmacyAdmn;
	
	public PharmacyOrderDTO() {}
	
	public PharmacyOrderDTO(int id, LocalDate limitDate, boolean isFinished, int idPharmacyAdmn) {
		super();
		this.id = id;
		this.limitDate = limitDate;
		this.isFinished = isFinished;
		this.idPharmacyAdmn = idPharmacyAdmn;
	}

	
}

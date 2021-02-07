package rs.ac.uns.ftn.isaproject.dto;

public class DrugOfferAndOrderDTO {

	public DrugOfferDTO drugOfferDTO;
	public PharmacyOrderDTO pharmacyOrderDTO;
	
	public DrugOfferAndOrderDTO() { }

	public DrugOfferAndOrderDTO(DrugOfferDTO drugOfferDTO, PharmacyOrderDTO pharmacyOrderDTO) {
		super();
		this.drugOfferDTO = drugOfferDTO;
		this.pharmacyOrderDTO = pharmacyOrderDTO;
	}
	
}

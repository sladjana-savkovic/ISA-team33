package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.PricelistDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pricelist;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PharmacyRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.PricelistRepository;

@Service
public class PricelistServiceImpl implements PricelistService {

	private PricelistRepository pricelistRepository;
	private PharmacyRepository pharmacyRepository;
	private DrugRepository drugRepository;
	
	@Autowired
	public PricelistServiceImpl(PricelistRepository pricelistRepository, PharmacyRepository pharmacyRepository, DrugRepository drugRepository) {
		this.pricelistRepository = pricelistRepository;
		this.pharmacyRepository = pharmacyRepository;
		this.drugRepository = drugRepository;
	}

	@Override
	public void save(PricelistDTO pricelistDTO) {
		
		Pricelist pricelist = new Pricelist();
		Pharmacy pharmacy = pharmacyRepository.getOne(pricelistDTO.idPharmacy);
		Drug drug = drugRepository.getOne(pricelistDTO.idDrug);
		
		pricelist.setPrice(pricelistDTO.price);
		pricelist.setStartDate(pricelistDTO.startDate);
		pricelist.setEndDate(pricelistDTO.endDate);
		pricelist.setDrug(drug);
		pricelist.setPharmacy(pharmacy);
		pricelist.setCreationDate(LocalDate.now());

		pricelistRepository.save(pricelist);
		
	}
}
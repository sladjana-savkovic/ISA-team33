package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.PharmacyPriceDTO;
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
	
	
	public Collection<Pricelist> getPharmaciesAndPriceByDrugId(int drugId) {		
		Collection<Pricelist> newPricelist = new ArrayList<Pricelist>();		
		Collection<Pricelist> pricelist = pricelistRepository.getByDrugId(drugId);
		for (Pricelist p : pricelist) {			
			if (LocalDate.now().isAfter(p.getStartDate()) && LocalDate.now().isBefore(p.getEndDate())) {
				newPricelist.add(p);				
			}			
		}		
		return newPricelist;
	}
	
	
}
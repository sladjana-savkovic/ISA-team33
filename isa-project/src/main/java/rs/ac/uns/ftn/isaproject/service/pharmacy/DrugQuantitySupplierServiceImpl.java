package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddDrugQuantitySupplierDTO;
import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantitySupplier;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantitySupplierRepository;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugRepository;
import rs.ac.uns.ftn.isaproject.repository.users.SupplierRepository;

@Service
public class DrugQuantitySupplierServiceImpl implements DrugQuantitySupplierService {
		
	private DrugQuantitySupplierRepository quantitySupplierRepository;	
	private DrugRepository drugRepository;
	private SupplierRepository supplierRepository;
	
	@Autowired
	public DrugQuantitySupplierServiceImpl(DrugQuantitySupplierRepository quantitySupplierRepository, DrugRepository drugRepository, SupplierRepository supplierRepository) {
		this.quantitySupplierRepository = quantitySupplierRepository;
		this.drugRepository = drugRepository;
		this.supplierRepository = supplierRepository;
	}

	@Override
	public Collection<DrugQuantitySupplier> findBySupplierId(int id) {
		return quantitySupplierRepository.findBySupplierId(id);
	}

	@Override
	public void add(AddDrugQuantitySupplierDTO drugQuantityDTO) {
		DrugQuantitySupplier drugQuantitySupplier = quantitySupplierRepository.findBySupplierAndDrug(drugQuantityDTO.supplierId, drugQuantityDTO.drugId);
		if (drugQuantitySupplier == null) {
			drugQuantitySupplier = new DrugQuantitySupplier();	
			drugQuantitySupplier.setDrug(drugRepository.getOne(drugQuantityDTO.drugId));
			drugQuantitySupplier.setSupplier(supplierRepository.getOne(drugQuantityDTO.supplierId));
		}		
		drugQuantitySupplier.setQuantity(drugQuantityDTO.quantity);
		quantitySupplierRepository.save(drugQuantitySupplier);		
	}

}

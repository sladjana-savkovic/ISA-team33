package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantitySupplier;
import rs.ac.uns.ftn.isaproject.repository.pharmacy.DrugQuantitySupplierRepository;

@Service
public class DrugQuantitySupplierServiceImpl implements DrugQuantitySupplierService {
	
	
	private DrugQuantitySupplierRepository quantitySupplierRepository;	
	
	@Autowired
	public DrugQuantitySupplierServiceImpl(DrugQuantitySupplierRepository quantitySupplierRepository) {
		this.quantitySupplierRepository = quantitySupplierRepository;
	}

	@Override
	public Collection<DrugQuantitySupplier> findBySupplierId(int id) {
		return quantitySupplierRepository.findBySupplierId(id);
	}

}

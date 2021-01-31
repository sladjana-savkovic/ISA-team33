package rs.ac.uns.ftn.isaproject.service.pharmacy;

import java.util.Collection;

import rs.ac.uns.ftn.isaproject.model.pharmacy.Drug;

public interface DrugQuantityPharmacyService {

	Collection<Drug> findDrugsByPharmacyId(int id);
}

package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantitySupplier;

public interface DrugQuantitySupplierRepository extends JpaRepository<DrugQuantitySupplier, Integer> {

	@Query(value = "select count(*) from drug_quantity_order o, drug_quantity_supplier s "
			+ " where o.drug_id=s.drug_id and s.supplier_id=?1 and o.pharmacy_order_id=?2 and o.quantity <= s.quantity ",
			nativeQuery = true)	
	int getNumberOfAvailableDrugs(int supplierId, int orderId);		

	@Query("select d from DrugQuantitySupplier d where d.supplier.id = ?1")
	Collection<DrugQuantitySupplier> findBySupplierId(int id);
	
	@Query(value = "select * from drug_quantity_supplier d where d.supplier_id = ?1 and d.drug_id = ?2 limit 1", nativeQuery = true)
	DrugQuantitySupplier findBySupplierAndDrug(int supplierId, int drugId);
}

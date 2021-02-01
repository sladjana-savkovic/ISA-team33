package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;

public interface DrugQuantityOrderRepository extends JpaRepository<DrugQuantityOrder, Integer> {

	@Query("select o from DrugQuantityOrder o where o.pharmacyOrder.id = ?1")
	Collection<DrugQuantityOrder> findByPharmacyOrderId(int id);
}

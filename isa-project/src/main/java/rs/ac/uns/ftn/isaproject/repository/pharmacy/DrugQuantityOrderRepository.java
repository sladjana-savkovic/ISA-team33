package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantityOrder;

public interface DrugQuantityOrderRepository extends JpaRepository<DrugQuantityOrder, Integer> {

}

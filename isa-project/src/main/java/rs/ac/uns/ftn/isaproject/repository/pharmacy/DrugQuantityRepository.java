package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugQuantity;

public interface DrugQuantityRepository extends JpaRepository<DrugQuantity, Integer> {

}

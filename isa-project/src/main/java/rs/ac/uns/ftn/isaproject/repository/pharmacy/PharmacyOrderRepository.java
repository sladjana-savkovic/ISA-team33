package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isaproject.model.pharmacy.PharmacyOrder;

public interface PharmacyOrderRepository extends JpaRepository<PharmacyOrder, Integer> {

}

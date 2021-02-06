package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.isaproject.model.pharmacy.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Integer> {

	Pharmacy findOneById(int id);
	
}

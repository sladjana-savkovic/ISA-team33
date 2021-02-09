package rs.ac.uns.ftn.isaproject.repository.pharmacy;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.pharmacy.DrugReservation;

public interface DrugReservationRepository extends JpaRepository<DrugReservation, Integer>{

	@Query("select d from DrugReservation d where d.pharmacy.id = ?1")
	Collection<DrugReservation> findByPharmacyId(int id);
	
	Collection<DrugReservation> findByPatientIdAndIsDone(int id, boolean isDone);
}

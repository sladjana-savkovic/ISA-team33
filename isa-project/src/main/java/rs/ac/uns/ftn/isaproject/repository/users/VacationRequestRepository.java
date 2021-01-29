package rs.ac.uns.ftn.isaproject.repository.users;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.users.VacationRequest;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Integer> {

	@Query("select v from VacationRequest v where v.pharmacy.id = ?1")
	Collection<VacationRequest> findByPharmacyId(int id);
}

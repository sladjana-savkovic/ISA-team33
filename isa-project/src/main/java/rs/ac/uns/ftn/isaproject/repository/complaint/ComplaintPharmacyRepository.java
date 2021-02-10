package rs.ac.uns.ftn.isaproject.repository.complaint;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintPharmacy;

public interface ComplaintPharmacyRepository extends JpaRepository<ComplaintPharmacy, Integer> {

	@Query("select o from ComplaintPharmacy o where o.answered = ?1")
	Collection<ComplaintPharmacy> getAllByAnsweredAttribute(boolean answered);
}

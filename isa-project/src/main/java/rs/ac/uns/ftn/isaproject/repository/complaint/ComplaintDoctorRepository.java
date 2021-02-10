package rs.ac.uns.ftn.isaproject.repository.complaint;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.complaint.ComplaintDoctor;

public interface ComplaintDoctorRepository extends JpaRepository<ComplaintDoctor, Integer> {

	@Query("select o from ComplaintDoctor o where o.answered = ?1")
	Collection<ComplaintDoctor> getAllByAnsweredAttribute(boolean answered);
}

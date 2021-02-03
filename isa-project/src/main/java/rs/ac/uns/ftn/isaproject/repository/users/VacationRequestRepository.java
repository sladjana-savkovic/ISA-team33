package rs.ac.uns.ftn.isaproject.repository.users;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.users.Doctor;
import rs.ac.uns.ftn.isaproject.model.users.VacationRequest;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Integer> {

	@Query("select v from VacationRequest v where v.pharmacy.id = ?1")
	Collection<VacationRequest> findByPharmacyId(int id);
	
	@Query("select v from VacationRequest v where v.pharmacy.id = ?1 and v.doctor.id =?2")
	Collection<VacationRequest> findByDoctorPharmacyId(int id_pharmacy, int id_doctor);
	
	@Query("select v.doctor from VacationRequest v where v.id = ?1")
	Doctor findDoctorById(int id);
	
}

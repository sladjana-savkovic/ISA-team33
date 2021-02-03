package rs.ac.uns.ftn.isaproject.repository.users;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.users.WorkingTime;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Integer> {

	@Query("select w from WorkingTime w where w.pharmacy.id = ?1")
	Collection<WorkingTime> findByPharmacyId(int id);
	
	@Query("select w from WorkingTime w where w.pharmacy.id = ?1 and w.doctor.id = ?2")
	WorkingTime findByPharmacyDoctorId(int id_pharmacy, int id_doctor);
}

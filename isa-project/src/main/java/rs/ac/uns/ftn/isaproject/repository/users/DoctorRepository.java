package rs.ac.uns.ftn.isaproject.repository.users;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isaproject.model.enums.TypeOfDoctor;
import rs.ac.uns.ftn.isaproject.model.users.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
	
	@Query("select p.doctors from Pharmacy p where p.id = ?1")
	Collection<Doctor> findByPharmacyId(int id);
	
	Collection<Doctor> findByTypeOfDoctor(TypeOfDoctor type);
}

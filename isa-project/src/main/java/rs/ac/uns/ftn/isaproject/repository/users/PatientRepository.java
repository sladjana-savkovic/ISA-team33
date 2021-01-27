package rs.ac.uns.ftn.isaproject.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.isaproject.model.users.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}

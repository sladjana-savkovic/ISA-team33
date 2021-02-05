package rs.ac.uns.ftn.isaproject.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isaproject.model.users.SystemAdministrator;

public interface SystemAdministratorRepository extends JpaRepository<SystemAdministrator, Integer> {

}

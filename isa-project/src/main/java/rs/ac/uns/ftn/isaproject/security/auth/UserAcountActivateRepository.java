package rs.ac.uns.ftn.isaproject.security.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

@Repository("userRepository")
public interface UserAcountActivateRepository extends CrudRepository<UserEntity, String> {

	UserEntity findByEmailIdIgnoreCase(String emailId);
	
}

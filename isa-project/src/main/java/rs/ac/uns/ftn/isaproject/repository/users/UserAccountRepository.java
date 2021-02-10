package rs.ac.uns.ftn.isaproject.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isaproject.model.users.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
	
	UserAccount findByUsername(String username);

	UserAccount getOneByUserId(int user);
}

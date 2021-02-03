package rs.ac.uns.ftn.isaproject.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isaproject.model.users.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	
	Authority findByName(String name);
		
}

package rs.ac.uns.ftn.isaproject.security.auth;

import java.util.List;

import rs.ac.uns.ftn.isaproject.model.users.Authority;

public interface AuthorityService {
	List<Authority> findById(Long id);
	List<Authority> findByname(String name);
}

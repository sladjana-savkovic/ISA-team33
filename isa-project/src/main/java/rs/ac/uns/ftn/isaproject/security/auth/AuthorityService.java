package rs.ac.uns.ftn.isaproject.security.auth;

import rs.ac.uns.ftn.isaproject.model.users.Authority;

public interface AuthorityService {
	Authority findById(Long id);
	Authority findByname(String name);
}

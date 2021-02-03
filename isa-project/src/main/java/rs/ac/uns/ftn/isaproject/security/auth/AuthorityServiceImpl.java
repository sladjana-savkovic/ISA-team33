package rs.ac.uns.ftn.isaproject.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.users.Authority;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	  @Autowired
	  private AuthorityRepository authorityRepository;

	  @Override
	  public Authority findById(Long id) {
	    Authority auth = this.authorityRepository.getOne(id);
	    return auth;
	  }

	  @Override
	  public Authority findByname(String name) {
	    Authority auth = this.authorityRepository.findByName(name);
	    return auth;
	  }

}

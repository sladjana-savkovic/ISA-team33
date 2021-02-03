package rs.ac.uns.ftn.isaproject.service.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.model.users.Authority;
import rs.ac.uns.ftn.isaproject.model.users.User;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.repository.users.UserAccountRepository;
import rs.ac.uns.ftn.isaproject.security.auth.AuthorityService;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authService;

	@Override
	public UserAccount findByUsername(String username) throws UsernameNotFoundException {
		UserAccount u = userRepository.findByUsername(username);
		return u;
	}

	public UserAccount findById(Long id) throws AccessDeniedException {
		UserAccount u = userRepository.findById(id).orElseGet(null);
		return u;
	}

	public List<UserAccount> findAll() throws AccessDeniedException {
		List<UserAccount> result = userRepository.findAll();
		return result;
	}
	
	
	//cuvanje glavnih podataka o useru
	public void save(String username, String password, String role, boolean enabled, User user) {
		UserAccount userAccount = new UserAccount();
		
		userAccount.setUser(user);		
		userAccount.setUsername(username);		
		userAccount.setPassword(passwordEncoder.encode(password));  // pre nego sto postavimo lozinku u atribut hesiramo je
		userAccount.setEnabled(enabled);
		
		Authority auth = authService.findByname(role);
		userAccount.setAuthority(auth);
		
		userAccount = this.userRepository.save(userAccount);
	}

}

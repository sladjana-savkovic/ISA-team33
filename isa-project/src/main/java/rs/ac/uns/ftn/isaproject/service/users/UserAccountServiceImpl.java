package rs.ac.uns.ftn.isaproject.service.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.dto.AddPatientDTO;
import rs.ac.uns.ftn.isaproject.model.users.Authority;
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

	/*
	@Override
	public UserAccount save(UserRequest userRequest) {
		UserAccount u = new UserAccount();
		u.setUsername(userRequest.getUsername());
		// pre nego sto postavimo lozinku u atribut hesiramo je
		u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		u.setFirstName(userRequest.getFirstname());
		u.setLastName(userRequest.getLastname());
		u.setEnabled(true);
		
		Authority auth = authService.findByname("ROLE_PATIENT");
		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		u.setAuthority(auth);
		
		u = this.userRepository.save(u);
		return u;
	}
	*/
	
	
	public UserAccount save(AddPatientDTO addPatientDTO) {
		UserAccount u = new UserAccount();
		u.setUsername(addPatientDTO.email);
		// pre nego sto postavimo lozinku u atribut hesiramo je
		u.setPassword(passwordEncoder.encode(addPatientDTO.password));
		u.setEnabled(true);
		
		Authority auth = authService.findByname("ROLE_PATIENT");
		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		u.setAuthority(auth);
		
		u = this.userRepository.save(u);
		return u;
	}

}

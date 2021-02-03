package rs.ac.uns.ftn.isaproject.service.users;

import java.nio.file.AccessDeniedException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isaproject.exceptions.BadRequestException;
import rs.ac.uns.ftn.isaproject.model.users.Authority;
import rs.ac.uns.ftn.isaproject.model.users.User;
import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.repository.users.UserAccountRepository;
import rs.ac.uns.ftn.isaproject.security.auth.AuthorityService;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	private UserAccountRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private AuthorityService authService;
	
	@Autowired
	public UserAccountServiceImpl(UserAccountRepository userRepository,PasswordEncoder passwordEncoder,AuthorityService authService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authService = authService;
	}

	@Override
	public void save(String username, String password, String role, boolean enabled, User user) {
		UserAccount userAccount = new UserAccount();
		
		userAccount.setUser(user);		
		userAccount.setUsername(username);		
		userAccount.setPassword(passwordEncoder.encode(password)); 
		userAccount.setEnabled(enabled);
		
		Authority auth = authService.findByname(role);
		userAccount.setAuthority(auth);
		userAccount = this.userRepository.save(userAccount);
	}

	@Override
	public void updatePassword(Long id,String oldPassword, String newPassword) throws BadRequestException {
		UserAccount userAccount = userRepository.getOne(id);
		
		if(!passwordEncoder.matches(oldPassword, userAccount.getPassword())) {
			throw new BadRequestException("Wrong old password.");
		}
		
		userAccount.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(userAccount);
	}

	@Override
	public UserAccount findByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public UserAccount findById(Long id) throws AccessDeniedException {
		return userRepository.findById(id).orElseGet(null);
	}

	@Override
	public UserAccount findByUserId(int id) {
		Collection<UserAccount> userAccounts = userRepository.findAll();
		
		for(UserAccount userAccount:userAccounts) {
			if(userAccount.getUser().getId() == id) {
				
				return userAccount;
			}
		}
		return null;
	}

}

package rs.ac.uns.ftn.isaproject.security.auth;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import rs.ac.uns.ftn.isaproject.model.users.UserAccount;
import rs.ac.uns.ftn.isaproject.repository.users.UserAccountRepository;
import rs.ac.uns.ftn.isaproject.service.users.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserAccountRepository userAccountRepository;
		
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));

			// Ubaci korisnika u trenutni security kontekst
			SecurityContextHolder.getContext().setAuthentication(authentication);

			UserAccount userAccount = (UserAccount) authentication.getPrincipal();
			String jwt = tokenUtils.generateToken(userAccount.getUsername(), userAccount.getId(), 
												userAccount.getAuthority().getName(), userAccount.getUser().getId(), userAccount.isActive());
			
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
		}
		catch (BadCredentialsException e) {
			return new ResponseEntity<>("Bad credentials.", HttpStatus.UNAUTHORIZED);
		}
		catch (Exception e) {
			return new ResponseEntity<>("An error occurred while sending request for log in.", HttpStatus.BAD_REQUEST);
		}
	}
		
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}
	

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
	
		
	/* kad klikne na link iz mejla, aktivira nalog */
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
	{
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if(token != null) {
      	UserAccount user = userAccountRepository.findByUsername(token.getUserAccount().getUsername());
      		user.setEnabled(true);
      		user.setActive(true);
      		userAccountRepository.save(user);
      		modelAndView.setViewName("accountVerified");
		}
		else {
			modelAndView.addObject("message","The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}
}	


package rs.ac.uns.ftn.isaproject.security.activation;

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

@RestController
@RequestMapping(value = "/activation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivationController {

	private ConfirmationTokenRepository confirmationTokenRepository;	
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	public ActivationController(ConfirmationTokenRepository confirmationTokenRepository, UserAccountRepository userAccountRepository) {		
		this.confirmationTokenRepository = confirmationTokenRepository;
		this.userAccountRepository = userAccountRepository;
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

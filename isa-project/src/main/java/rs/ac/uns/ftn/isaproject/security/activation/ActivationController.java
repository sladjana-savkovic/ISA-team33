package rs.ac.uns.ftn.isaproject.security.activation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import rs.ac.uns.ftn.isaproject.service.users.UserAccountService;

@RestController
@RequestMapping(value = "/activation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivationController {

	private ConfirmationTokenRepository confirmationTokenRepository;	
	private UserAccountService userAccountService;
	
	@Autowired
	public ActivationController(ConfirmationTokenRepository confirmationTokenRepository, UserAccountService userAccountService) {		
		this.confirmationTokenRepository = confirmationTokenRepository;
		this.userAccountService = userAccountService;
	}
	
	
	/* kad klikne na link iz mejla, aktivira nalog */
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
	{
		if(userAccountService.confirmUserAccount(confirmationToken)) {
      		modelAndView.setViewName("accountVerified");
		}
		else {
			modelAndView.addObject("message","The link is invalid or broken!");
			modelAndView.setViewName("error");
		}
		return modelAndView;
	}	
	
}

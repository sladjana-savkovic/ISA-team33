package rs.ac.uns.ftn.isaproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import rs.ac.uns.ftn.isaproject.security.auth.RestAuthenticationEntryPoint;
import rs.ac.uns.ftn.isaproject.security.auth.TokenAuthenticationFilter;
import rs.ac.uns.ftn.isaproject.security.auth.TokenUtils;
import rs.ac.uns.ftn.isaproject.service.users.CustomUserDetailsService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	private TokenUtils tokenUtils;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

				.authorizeRequests()
						.antMatchers("/auth/**").permitAll()
						.antMatchers("/h2-console/**").permitAll()
						.antMatchers("/api/country").permitAll()
						.antMatchers("/api/city/**").permitAll()
						.antMatchers("/register").permitAll()          //dodato zbog aktiviranja
						.antMatchers("/auth/confirm-account").permitAll()   //naloga preko mejla
						
				
				.anyRequest().authenticated().and()
				.cors().and()

				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
						BasicAuthenticationFilter.class);
		http.csrf().disable();
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/patient"); //registracija pacijenta
		web.ignoring().antMatchers(HttpMethod.GET, "/api/drug");
		web.ignoring().antMatchers(HttpMethod.POST, "/api/drug/search");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/drug/all");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/drug/**/substitute");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/pricelist/**/drug");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/report/**/pharmacy");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/report/**/appointment");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/report/**/drug");
		web.ignoring().antMatchers(HttpMethod.GET, "/api/report/**/income/**/**");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js");
	}

}

package fr.univamu.epu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/favicon.ico", "/*.js", "/*.css");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		RequestMatcher publicRoutes = new OrRequestMatcher(
				new AntPathRequestMatcher("/index.html", "GET"),
				new AntPathRequestMatcher("/", "GET"),
				new AntPathRequestMatcher("/login", "GET"),
				new AntPathRequestMatcher("/steps", "GET"),
				new AntPathRequestMatcher("/steps/*", "GET"),
				new AntPathRequestMatcher("/paths", "GET"),
				new AntPathRequestMatcher("/formations", "GET"));

		http.cors().and()
				.httpBasic().and()
				.authorizeRequests()
				.requestMatchers(publicRoutes)
				.permitAll()
				.anyRequest().authenticated()
				.and().csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}

package fr.univamu.epu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/favicon.ico", "/*.js", "/*.css");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				.httpBasic().and()
				.authorizeRequests()
				.antMatchers("/index.html", "/", "/home", "/login")
				.permitAll()
				.anyRequest().authenticated()
				.and().csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}

package conecta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationProvider authProvider;

	/*@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			// rest Login
			http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasRole("ADMIN").and().httpBasic().and().csrf()
					.disable();
		}
	}*/

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// form login
		http.authorizeRequests().antMatchers("/", "/login.xhtml", "/javax.faces.resource/**").permitAll().anyRequest()
				.fullyAuthenticated().and().formLogin().loginPage("/login.xhtml").defaultSuccessUrl("/index.xhtml")
				.failureUrl("/login.xhtml?authfailed=true").permitAll().and().logout().logoutSuccessUrl("/login.xhtml")
				.logoutUrl("/j_spring_security_logout").and().csrf().disable();

		// allow to use ressource links like pdf
		http.headers().frameOptions().sameOrigin();
	}

	/*public static boolean isUserAdmin() {
		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (auth.getAuthority().equalsIgnoreCase("ADMIN"))
				return true;
		}

		return false;
	}*/

}

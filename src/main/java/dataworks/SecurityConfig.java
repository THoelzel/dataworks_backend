package dataworks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ResourceLoader resourceLoader;

	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
		.csrf().disable()
		//.headers().disable()    
		.authorizeRequests()
		.antMatchers("/authenticated").permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic().and()
		.formLogin().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/**
	 * Configure sample user accounts.
	 *
	 * @param auth the auth
	 * @throws Exception the exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		Resource resource = resourceLoader.getResource("classpath:users.cfg");
		InputStream inputStream = resource.getInputStream();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		// Sample user accounts
		auth
		.inMemoryAuthentication()
		.withUser("user").password(bufferedReader.readLine()).roles("USER");
		
		auth
		.inMemoryAuthentication()
		.withUser("admin").password(bufferedReader.readLine()).roles("ADMIN");
	}
}
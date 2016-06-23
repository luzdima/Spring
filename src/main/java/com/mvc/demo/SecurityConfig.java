package com.mvc.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired 
	private DataSource dataSource;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select username, password, enabled from users where username=?")
			.authoritiesByUsernameQuery("select username, role from user_roles where username=?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers("/categories/*").hasRole("ADMIN")  
		.antMatchers("/products/image*").hasAnyRole("ADMIN", "USER")
		.antMatchers("/products/*").hasRole("ADMIN") 
		.antMatchers("/*").hasAnyRole("ADMIN", "USER")
		.and()
			.formLogin().loginPage("/login")
			.defaultSuccessUrl("/products")
			.failureUrl("/login?error")
			.usernameParameter("username").passwordParameter("password")				
		.and()
			.logout().logoutSuccessUrl("/login?logout"); 
	}
}

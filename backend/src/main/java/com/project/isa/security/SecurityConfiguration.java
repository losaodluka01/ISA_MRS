package com.project.isa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		
	}


		@Override
		protected void configure(HttpSecurity httpSecurity) throws Exception {
			httpSecurity.csrf().disable()
			.authorizeRequests()

			.antMatchers("/admin/create_clinic").hasAuthority("ADMIN_ROLE")
			.antMatchers("/admin/create_clinic_admin").hasAuthority("ADMIN_ROLE")
			.antMatchers("/admin/get_clinic_names").hasAuthority("ADMIN_ROLE")
			.antMatchers("/admin/approve_registration_request").hasAuthority("ADMIN_ROLE")
			.antMatchers("/admin/deny_registration_request").hasAuthority("ADMIN_ROLE")
			.antMatchers("/admin/get_registration_requests").hasAuthority("ADMIN_ROLE")
			.antMatchers("/user/update_profile").hasAuthority("PATIENT_ROLE")
			.antMatchers("/user/{username}").permitAll()
			.antMatchers("/user/check_verification/{username}").permitAll()
			.antMatchers("/user/verify_user/{username}").permitAll()
			.antMatchers("/user/register").permitAll()
			.antMatchers("/user/login").permitAll().anyRequest()
					.authenticated().and().exceptionHandling().and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and().httpBasic().and().csrf().disable();
			httpSecurity.cors();
			httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

		}

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
		configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}



	/*
	 * 
	 * @Bean public PasswordEncoder passwordEncoder() { System.out.println();
	 * System.out.println(NoOpPasswordEncoder.getInstance().toString()); return
	 * NoOpPasswordEncoder.getInstance(); }
	 */

}

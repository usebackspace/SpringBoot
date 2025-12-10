package com.example.project.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.project.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	 private final UserRepository userRepository;

    public WebSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/about", "/contact", "/cat1", "/cats", "/birds", "/register","/product-details/{id}").permitAll()
				.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.defaultSuccessUrl("/", true)   // go to home after login
				.permitAll()
			)
			.logout((logout) -> logout
			.logoutSuccessUrl("/")   // redirect to home page
			.permitAll());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

@Bean
public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
            .map(user -> org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword()) // Already hashed
                    .roles(user.getRole())
                    .build()
            )
            .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
}

}
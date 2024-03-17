package com.example.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//The @Configuration annotation tells Spring to use this class to configure Spring and Spring Boot itself.
// Any Beans specified in this class will now be available to Spring's Auto Configuration engine.
@Configuration
class SecurityConfig {
//    Spring Security expects a Bean to configure its Filter Chain
//    This is the chain of filters that Spring Security will use to secure our application.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        We can use the HttpSecurity object to configure the security of our application.
//        All HTTP requests to cashcards/ endpoints are required
//        to be authenticated using HTTP Basic Authentication security (username and password).
//        Also, do not require CSRF security.
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/cashcards/**")
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
//    Spring Security expects a Bean to configure the users that are allowed to authenticate with our application.
//    This is a simple in-memory user store with a single user.
    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails sarah = users
                .username("sarah1")
                .password(passwordEncoder.encode("abc123"))
                .roles() // No roles for now
                .build();
        return new InMemoryUserDetailsManager(sarah);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
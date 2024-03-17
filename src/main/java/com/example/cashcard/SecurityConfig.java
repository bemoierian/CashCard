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
//        We also require that the authenticated user has the role CARD-OWNER.
//        Also, do not require CSRF security.
//        Use CSRF protection for any request that could be processed by a browser by normal users.
//        If you are only creating a service that is used by non-browser clients,
//        you will likely want to disable CSRF protection.
        http
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/cashcards/**")
                    .hasRole("CARD-OWNER"))
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable());
        return http.build();
    }
//    Spring Security expects a Bean to configure the users that are allowed to authenticate with our application.
    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
//    This is a simple in-memory user store with two users.
//    The first user, sarah1, has the role CARD-OWNER.
        User.UserBuilder users = User.builder();
        UserDetails sarah = users
                .username("sarah1")
                .password(passwordEncoder.encode("abc123"))
                .roles("CARD-OWNER") // new role
                .build();
//    The second user, hank-owns-no-cards, has the role NON-OWNER.
        UserDetails hankOwnsNoCards = users
                .username("hank-owns-no-cards")
                .password(passwordEncoder.encode("qrs456"))
                .roles("NON-OWNER") // new role
                .build();
        return new InMemoryUserDetailsManager(sarah, hankOwnsNoCards);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
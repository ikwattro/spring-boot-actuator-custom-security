package com.ikwattro.spring.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SetLoggerAccessChecker setLoggerAccessChecker;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").httpBasic();
        http.csrf().disable().requestMatcher(EndpointRequest.to(LoggersEndpoint.class)).authorizeRequests((requests) -> {
            requests.anyRequest().access("@setLoggerAccessChecker.isAuthorizedToChangeLogs(authentication, request)");
        });
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        UserDetails user2 =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("password")
                        .roles("ADMIN", "USER")
                        .build();

        return new InMemoryUserDetailsManager(user, user2);
    }
}

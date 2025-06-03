package com.burat.simpel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableScheduling
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/register/**").hasAuthority("admin")
                .antMatchers("/account-management/**").hasAuthority("admin")
                .antMatchers("/comp-dict/add/**").hasAuthority("admin")
                .antMatchers("/comp-dict/update/**").hasAuthority("admin")
                .antMatchers("/comp-dict/delete/**").hasAuthority("admin")
                .antMatchers("/comp-dict/detail/**").permitAll()
                .antMatchers("/comp-dict/**").permitAll()
                .antMatchers("/competency-model/add/**").hasAuthority("admin")
                .antMatchers("/competency-model/delete/**").hasAuthority("admin")
                .antMatchers("/competency-model/update/**").hasAuthority("admin")
                .antMatchers("/competency-model").permitAll()
                .antMatchers("/training-catalog/add/**").hasAuthority("admin")
                .antMatchers("/training-catalog/delete/**").hasAuthority("admin")
                .antMatchers("/training-catalog/update/**").hasAuthority("admin")
                .antMatchers("/training-catalog/detail/**").permitAll()
                .antMatchers("/training-catalog/**").permitAll()
                .antMatchers("/do-assessment/**").hasAuthority("assessor")
                .antMatchers("/review/list-user/**").hasAnyAuthority("admin","assessor")
                .antMatchers("/review/user/**").hasAnyAuthority("admin","assessor")
                .antMatchers("/review/me/**").hasAnyAuthority("user")
                .antMatchers("/review/**").hasAnyAuthority("admin","assessor","user")
                .antMatchers("/training-plan/update/**").hasAnyAuthority("admin")
                .antMatchers("/training-plan/delete/**").hasAnyAuthority("admin")
                .antMatchers("/training-plan/**").hasAnyAuthority("admin","executive")
                .antMatchers("/training-plan").hasAnyAuthority("admin","executive")
                .antMatchers("/training-recommendation/**").hasAnyAuthority("admin","executive")
                .antMatchers("/history-assessment/**").hasAnyAuthority("admin","assessor")
                .antMatchers("/time-management/**").hasAnyAuthority("admin")
                .antMatchers("/dashboard/**").hasAnyAuthority("user")
                .antMatchers("/report/**").hasAnyAuthority("admin","executive")
                .antMatchers("/review-training/**").hasAnyAuthority("user")
                .antMatchers("/view-review/**").hasAnyAuthority("admin","executive")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll();
        return http.build();
    }
    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}

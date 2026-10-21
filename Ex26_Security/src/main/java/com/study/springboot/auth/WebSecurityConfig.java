package com.study.springboot.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
{
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
            DaoAuthenticationProvider authenticationProvider) throws Exception 
    {
        http.authenticationProvider(authenticationProvider)
	        .csrf(csrf -> csrf.disable()) // CSRF 비활성화
	        .cors(cors -> cors.disable()) // CORS 비활성화
            .authorizeHttpRequests(auth -> auth
           		.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/guest/**").permitAll()
                .requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()    // 어떠한 요청이라도 인증필요
            )

            .formLogin(form -> form.permitAll())   // 기본 로그인 페이지

            .logout(logout -> logout.permitAll());   // 로그아웃 기본설정 (/logout으로 인증해제)

        return http.build();
    }
    
    @Bean
    InMemoryUserDetailsManager users(PasswordEncoder passwordEncoder) 
    {
        UserDetails user = User.withUsername("user")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER")    // ROLE_USER 에서 ROLE_는 자동으로 붙는다
                        .build();
        UserDetails admin = User.withUsername("admin")
                        .password(passwordEncoder.encode("1234"))
                        .roles("ADMIN")
                        .build();
        
        // 메모리에 사용자 정보를 담는다.
        return new InMemoryUserDetailsManager(user,admin);
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(
            InMemoryUserDetailsManager userDetailsService,
            PasswordEncoder passwordEncoder) 
    {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // passwordEncoder() 추가
    @Bean
    PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }
}
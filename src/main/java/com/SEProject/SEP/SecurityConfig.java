package com.SEProject.SEP;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티의 제어를 받도록함
public class SecurityConfig {
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
            http
                    .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests// 로그인하지 않더라도 모든 페이지에 접근할 수 있도록
                            .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                    .csrf((csrf) -> csrf
                            .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                    .headers((headers) -> headers
                            .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))//SAMEORIGIN 프레임에 포함된 웹 페이지가 동일한 사이트에서 제공할 때에만 사용이 허락되도록
                    .formLogin((formLogin) -> formLogin
                            .loginPage("/user/login")
                            .defaultSuccessUrl("/"))
                    .logout((logout) -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                            .logoutSuccessUrl("/")//로그아웃 성공시 루트페이지로 이동
                            .invalidateHttpSession(true))
            ;
            return http.build();
        }
            @Bean//PasswordEncoder 객체를 빈으로 등록해서 사용
            PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
            }

             @Bean
             AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                     throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
    }

        }


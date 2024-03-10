package com.aluufc.demoparkingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static
        org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableMethodSecurity
public class SpringSecurityConfig {
    /*A anotação @Bean é usada para indicar que um método de
     uma classe de configuração retorna um objeto que deve ser
     gerenciado pelo contêiner Spring.*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth ->
                        // requestMatchers define permissões de acesso
                        auth.requestMatchers(
                                antMatcher(HttpMethod.POST,"/ap1/v1/usuarios"),
                                antMatcher(HttpMethod.POST,"/ap1/v1/auth"))
                                 .permitAll().anyRequest().authenticated())
            .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}

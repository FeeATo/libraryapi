package io.github.feeato.libraryapi.config;

import io.github.feeato.libraryapi.security.CustomUserDetailsService;
import io.github.feeato.libraryapi.security.LoginSocialSuccessHandler;
import io.github.feeato.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    /*Esse cara aqui vai sobreescrever o SecurityFilterChain padrão (que gera o formulariozinho no browser ou o que gera a senha e usa a autenticação basic*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(configurer->configurer.loginPage("/login.html").successForwardUrl()) //isso aqui define uma página customizada de login
//                .formLogin(Customizer.withDefaults()) //habilita o formulário de login javascript lá no navegador
                .httpBasic(Customizer.withDefaults()) //habilita a autenticação basic
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();
//                    authorize.requestMatchers(HttpMethod.POST, "/autores").hasRole("ADMIN"); //dá pra fazer isso também
//                    authorize.requestMatchers( "/autores/**").hasRole("ADMIN");
//                    authorize.requestMatchers("/livros/**").hasAnyRole("TECNICO", "ADMIN");
                    authorize.anyRequest().authenticated();
                })
                .oauth2Login(oauth2-> {
                    oauth2.successHandler(successHandler);
                })
                .build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthoritiesMapper() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

//    @Bean
//    public UserDetailsService userDetailsService(UsuarioService usuarioService) { //desligando esse cara para usar o CustomAuthenticationProvider
////        UserDetails user1 = User.builder()
////                .username("user")
////                .password(encoder.encode("123"))
////                .roles("USER")
////                .build();
////        UserDetails user2 = User.builder()
////                .username("admin")
////                .password(encoder.encode("123"))
////                .roles("ADMIN")
////                .build();
////
////        return new InMemoryUserDetailsManager(user1, user2);
//        return new  CustomUserDetailsService(usuarioService);
//    }
}

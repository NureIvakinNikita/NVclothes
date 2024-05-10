package com.example.nvclothes.config;


import com.example.nvclothes.repository.interfaces.ClientEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    private final ClientEntityRepositoryInterface clientEntityRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/all-products/add-to-cart", "/make-order").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendRedirect(request.getContextPath() + "/registration"));


        httpSecurity.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

   /* @Bean
    public UserDetailsService getUserDetails() {
        return userName -> clientEntityRepository.getClientEntityById(Long.parseLong(userName))
                .orElseThrow(() -> new UsernameNotFoundException(""));

    }*/

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

 /*httpSecurity
                .csrf().disable() // TODO clarify and fix
                .authorizeHttpRequests()
                .requestMatchers("/auth/**", "/registration/**")
                .permitAll()
                .anyRequest()
                .authenticated();*/
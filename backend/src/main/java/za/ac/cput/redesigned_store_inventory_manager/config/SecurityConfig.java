package za.ac.cput.redesigned_store_inventory_manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import za.ac.cput.redesigned_store_inventory_manager.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CsrfConfigurer<HttpSecurity> csrf) {
                csrf.disable();
            }
        });

        http.headers(new Customizer<HeadersConfigurer<HttpSecurity>>() {
            @Override
            public void customize(HeadersConfigurer<HttpSecurity> headers) {
                headers.frameOptions(new Customizer<HeadersConfigurer<HttpSecurity>.FrameOptionsConfig>() {
                    @Override
                    public void customize(HeadersConfigurer<HttpSecurity>.FrameOptionsConfig frame) {
                        frame.sameOrigin();
                    }
                });
            }
        });

        http.authorizeHttpRequests(
                new Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry>() {
                    @Override
                    public void customize(
                            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {

                        auth.requestMatchers("/h2-console/**").permitAll();
                        auth.requestMatchers(HttpMethod.POST, "/api/customer-auth/register").permitAll();
                        auth.requestMatchers("/api/auth/login", "/api/customer-auth/login").authenticated();
                        auth.requestMatchers(HttpMethod.GET, "/api/v1/products/**", "/api/inventory/**")
                                .hasAnyRole("EMPLOYEE", "CUSTOMER");
                        auth.requestMatchers("/api/purchase/**").hasAnyRole("EMPLOYEE", "CUSTOMER");
                        auth.anyRequest().hasRole("EMPLOYEE");
                    }
                });

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
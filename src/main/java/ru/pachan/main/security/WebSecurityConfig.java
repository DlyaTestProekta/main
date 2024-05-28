package ru.pachan.main.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.pachan.main.exception.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Value("${spring.boot.admin.client.username}")
    String adminUsername;

    @Value("${spring.boot.admin.client.password}")
    String adminPassword;

    private final RequestProvider requestProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public WebSecurityConfig(
            RequestProvider requestProvider,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint
    ) {
        this.requestProvider = requestProvider;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .exceptionHandling(it -> it.authenticationEntryPoint(customAuthenticationEntryPoint))
                .authorizeHttpRequests(it -> {
                    it
                            .requestMatchers("api/auth/**").hasAuthority("VerifiedToken")
                            .requestMatchers("actuator/**").hasAuthority("ActuatorAdmin")
                            .anyRequest().authenticated();
                })
                .addFilterBefore(
                        new JwtFilter(requestProvider, adminUsername, adminPassword),
                        BasicAuthenticationFilter.class
                )
                .httpBasic(Customizer.withDefaults())
                .cors(Customizer.withDefaults()); // Настроить - в хедере возвращать URL фронта
        return httpSecurity.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("api/auth/generate")
                .requestMatchers("swagger")
                .requestMatchers("swagger-ui/**")
                .requestMatchers("apiDocs/**");
    }

//    @Bean
//    void addCorsMappings(CorsRegistry corsRegistry) {
//         TODO
//        corsRegistry.addMapping("/api/**")
//                .allowedOrigins("https://domain2.com")
//                .allowedMethods("PUT", "DELETE")
//                .allowedHeaders("header1", "header2", "header3")
//                .exposedHeaders("header1", "header2")
//                .allowCredentials(true).maxAge(3600);
//    }
}

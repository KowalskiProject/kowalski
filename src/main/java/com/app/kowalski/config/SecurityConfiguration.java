package com.app.kowalski.config;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.app.kowalski.da.entities.KowalskiUserRole;
import com.app.kowalski.security.jwt.JWTAuthenticationFilter;
import com.app.kowalski.security.jwt.JWTAuthorizationFilter;
import com.app.kowalski.security.jwt.JWTHelperService;

@Configuration
@PropertySource("classpath:application.properties")
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

    private static Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Value("${kowalski.login.url}")
	private String SIGN_UP_URL;

    @Autowired
    private JWTHelperService jwtHelperService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource())
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, this.SIGN_UP_URL)
                .permitAll()
                //.anyRequest().hasAnyAuthority("USER", "ADMIN")
                .anyRequest().hasAnyAuthority(Arrays.stream(KowalskiUserRole.values()).map(Enum::name).toArray(String[]::new))
                .and()
                .addFilterBefore(new JWTAuthenticationFilter(authenticationManager(),jwtHelperService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), jwtHelperService), UsernamePasswordAuthenticationFilter.class)
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET","PUT","DELETE","POST","HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }




    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui.html", "/webjars/**");
    }

}

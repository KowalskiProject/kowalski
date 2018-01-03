package com.app.kowalski.config;



import com.app.kowalski.JWTSupport.JWTAuthenticationFilter;
import com.app.kowalski.JWTSupport.JWTAuthorizationFilter;
import com.app.kowalski.JWTSupport.JWTHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

	public static final String ROLE_USER = "USER";
	public static final String ROLE_ADMIN = "ADMIN";

    @Value("${kowalski.login_url ?:/login}")
	private String SIGN_UP_URL = "";

    @Autowired
    private JWTHelperService jwtHelperService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, this.SIGN_UP_URL)
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                .and()
                    .addFilterBefore(new JWTAuthenticationFilter(authenticationManager(),jwtHelperService), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), jwtHelperService), UsernamePasswordAuthenticationFilter.class)
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles(ROLE_USER);
        auth.inMemoryAuthentication().withUser("admin").password("password").roles(ROLE_ADMIN);

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui.html", "/webjars/**");
    }

}

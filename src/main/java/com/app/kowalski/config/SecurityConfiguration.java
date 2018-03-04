package com.app.kowalski.config;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.app.kowalski.security.jwt.JWTAuthenticationFilter;
import com.app.kowalski.security.jwt.JWTAuthorizationFilter;
import com.app.kowalski.security.jwt.JWTHelperService;
import com.app.kowalski.services.KowalskiUserService;

@Configuration
@PropertySource("classpath:application.properties")
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

    private static Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);

	public static final String ROLE_USER = "USER";
	public static final String ROLE_ADMIN = "ADMIN";

    @Value("${kowalski.login.ad_domain}")
	public String ad_domain;

    @Value("${kowalski.login.ad_url}")
    public String ad_url;

    @Value("${kowalski.login.url}")
	private String SIGN_UP_URL;

    @Value("${kowalski.login.local.user}")
    private String localUser;

    @Value("${kowalski.login.local.password}")
    private String localUserPassword;

    @Autowired
    private JWTHelperService jwtHelperService;

    @Autowired
    private KowalskiUserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, this.SIGN_UP_URL)
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                .and()
                    .addFilterBefore(new JWTAuthenticationFilter(authenticationManager(),jwtHelperService, userService), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), jwtHelperService), UsernamePasswordAuthenticationFilter.class)
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, AuthenticationProvider provider) throws Exception {

        inMemoryConfigurer().withUser(localUser).password(localUserPassword)
                .authorities(ROLE_ADMIN)
                .and()
                .configure(auth);

        if(provider != null) {
            LOG.info("Using Active Directory Provider");
            auth.authenticationProvider(provider);
        }
    }

    @Bean
    public AuthenticationProvider addProvider(){
        try {
        	if (!this.ad_url.isEmpty()) {
        		testLdapConnection(this.ad_url);

                ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(this.ad_domain, this.ad_url);
                provider.setConvertSubErrorCodesToExceptions(true);
                provider.setUseAuthenticationRequestCredentials(true);
                return provider;
        	} else {
        		LOG.info("+++ No active directory URL found. Skipping provider...");
        	}
        } catch (Exception e){
            LOG.error("***** Could not create authentication provider *****", e);
        }
        return null;
    }

    private void testLdapConnection(String ldap_url) throws NamingException {
        Hashtable env = new Hashtable(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldap_url);
        DirContext ctx = new InitialDirContext(env);
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

    private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryConfigurer() {
        return new InMemoryUserDetailsManagerConfigurer<>();
    }

}

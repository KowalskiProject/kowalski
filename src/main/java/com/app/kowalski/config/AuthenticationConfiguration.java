package com.app.kowalski.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.ldap.authentication.NullLdapAuthoritiesPopulator;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.util.ObjectUtils;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

@Configuration
public class AuthenticationConfiguration {

    private static Logger LOG = LoggerFactory.getLogger(AuthenticationConfiguration.class);


    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    @Value("${kowalski.login.type:#{null}}")
    public String login_type;

    @Value("${kowalski.login.ad.domain:#{null}}")
    public String ad_domain;

    @Value("${kowalski.login.ad.url:#{null}}")
    public String ad_url;

    @Value("${kowalski.login.ldap.url:#{null}}")
    public String ldap_url;

    @Value("${kowalski.login.local.user}")
    private String localUser;

    @Value("${kowalski.login.local.password}")
    private String localUserPassword;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        configureLocalAuth(auth);

        try {
            switch (parseLoginType(this.login_type)) {
                case AD: {
                    configureAD(auth);
                }
                ;
                break;
                case LDAP: {
                    configureLDAP(auth);
                }
                ;
                break;
                default: {
                    LOG.error("Invalid Login Type");
                }
            }
        }catch (Exception e){
            LOG.error("***** Could not configure authentication provider *****", e);
        }
    }

    private void configureLDAP(AuthenticationManagerBuilder auth) throws Exception {
        if(!ObjectUtils.isEmpty(this.ldap_url)) {
            auth.ldapAuthentication()
                    .contextSource()
                    .url(this.ldap_url)
                    .and()
                    .userDnPatterns("cn={0},ou=users")
                    .userSearchBase("ou=users")
                    .ldapAuthoritiesPopulator(new NullLdapAuthoritiesPopulator());
        }else{
            throw new IllegalArgumentException("+++ No LDAP URL found.");
        }
    }

    private void configureAD(AuthenticationManagerBuilder auth) throws Exception {
        try {
            if (!ObjectUtils.isEmpty(this.ad_url)) {
                AuthenticationConfiguration.testLdapConnection(this.ad_url);

                ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(this.ad_domain, this.ad_url);
                provider.setConvertSubErrorCodesToExceptions(true);
                provider.setUseAuthenticationRequestCredentials(true);
                auth.authenticationProvider(provider);
            } else {
                throw new IllegalArgumentException("+++ No active directory URL found.");
            }
        } catch (Exception e){
            throw e;
        }

    }

    private void configureLocalAuth(AuthenticationManagerBuilder auth) throws Exception {
        if(!ObjectUtils.isEmpty(localUser) && !ObjectUtils.isEmpty(localUserPassword)) {
            inMemoryConfigurer().withUser(localUser).password(localUserPassword)
                    .authorities(ROLE_ADMIN)
                    .and()
                    .configure(auth);
        }else{
            LOG.info("Skipping Local authentication");
        }
    }

    private LoginType parseLoginType(String login_type) {
        if(ObjectUtils.isEmpty(login_type))
            return null;

        if(login_type.toUpperCase().equals("AD"))
            return LoginType.AD;
        else if(login_type.toUpperCase().equals("LDAP"))
            return  LoginType.LDAP;
        return null;
    }

    private static void testLdapConnection(String ldap_url) throws NamingException {
        Hashtable env = new Hashtable(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldap_url);
        DirContext ctx = new InitialDirContext(env);
    }

    private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryConfigurer() {
        return new InMemoryUserDetailsManagerConfigurer<>();
    }

    public enum LoginType {
        AD,
        LDAP
    }
}

package com.app.kowalski.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import com.app.kowalski.da.repositories.KowalskiUserRepository;

@Component
public class KowalskiAuthoritiesPopulator implements LdapAuthoritiesPopulator, UserDetailsContextMapper {

    @Autowired
    private KowalskiUserRepository kwuRepo;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {

    	if(kwuRepo.findByUsername(username) != null) {
            return Arrays.asList(new SimpleGrantedAuthority(kwuRepo.findByUsername(username).getRole().toString()));
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {

        if(kwuRepo.findByUsername(username) != null) {
            return new User(username,"",  Arrays.asList(
            		new SimpleGrantedAuthority(kwuRepo.findByUsername(username).getRole().toString())));
        } else {
            return new User(username,"", Collections.emptyList());
        }


    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {

    }
}

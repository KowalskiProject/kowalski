package com.app.kowalski.config;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

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

import com.app.kowalski.da.entities.KowalskiUser;
import com.app.kowalski.da.repositories.KowalskiUserRepository;

@Component
public class KowalskiAuthoritiesPopulator implements LdapAuthoritiesPopulator, UserDetailsContextMapper {

    @Autowired
    private KowalskiUserRepository kwuRepo;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {

    	KowalskiUser user = kwuRepo.findByUsername(username);
        if(user != null) {
        	return user.getRoles().stream()
        			.map(kowalskiRole -> new SimpleGrantedAuthority(kowalskiRole.getRole()))
        			.collect(Collectors.toList());
            //return Arrays.asList(new SimpleGrantedAuthority("USER"));
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {

    	KowalskiUser user = kwuRepo.findByUsername(username);
        if(user != null) {
            //return new User(username, "",  Arrays.asList(new SimpleGrantedAuthority("USER")));
            return new User(username, "",  user.getRoles().stream().
            		map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList()));
        } else {
            return new User(username, "", Collections.emptyList());
        }


    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {

    }
}

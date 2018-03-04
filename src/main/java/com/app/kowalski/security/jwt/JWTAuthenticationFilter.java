package com.app.kowalski.security.jwt;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.kowalski.dto.KowalskiUserDTO;
import com.app.kowalski.exception.KowalskiUserNotFoundException;
import com.app.kowalski.services.KowalskiUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTHelperService jwtHelperService;
    private KowalskiUserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTHelperService jwtHelperService,
    		KowalskiUserService userService) {
        super.setAuthenticationManager(authenticationManager);
        this.jwtHelperService = jwtHelperService;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AccountCredentials credentials = new ObjectMapper()
                    .readValue(req.getInputStream(), AccountCredentials.class);

            // user is not registered in kowalski. Aborting authentication
            try {
				KowalskiUserDTO kowalskiUser = userService.getKowalskiUserByUsername(credentials.getUsername());
			} catch (KowalskiUserNotFoundException e) {
				System.out.println("User [" + credentials.getUsername() + "] not found in the system. Aborting...");
				throw new AuthenticationException("User not registered in the system") {};
			}

           return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            Collections.emptyList()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        jwtHelperService.addAuthentication(res,auth);
    }
}

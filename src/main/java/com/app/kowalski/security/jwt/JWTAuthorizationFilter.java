package com.app.kowalski.security.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTHelperService jwtHelperService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTHelperService jwtHelperService) {
        super(authenticationManager);
        this.jwtHelperService = jwtHelperService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(jwtHelperService.getHeaderString());

        if (header == null || !header.startsWith(jwtHelperService.getTokenPrefix())) {
            chain.doFilter(req, res);
            return;
        }

        Authentication authentication = jwtHelperService.getAuthentication(req);

        if(authentication != null){
            //Renew token
            jwtHelperService.addAuthentication(res,authentication);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

}

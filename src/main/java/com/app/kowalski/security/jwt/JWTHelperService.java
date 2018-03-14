package com.app.kowalski.security.jwt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JWTHelperService {

    private Logger LOG = LoggerFactory.getLogger(JWTHelperService.class);

    @Value("${kowalski.token.expiration ?: 180000}")
    private long EXPIRATION_TIME;
    @Value("${kowalski.token.secret ?:MySecret}")
    private String SECRET;
    private final String TOKEN_PREFIX = "Bearer";
    private final String HEADER_STRING = "Authorization";
    private final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    private final String EXPOSE_HEADERS_STRING = "Authorization";

    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    public String getSecret() {
        return SECRET;
    }

    public String getTokenPrefix() {
        return TOKEN_PREFIX;
    }

    public String getHeaderString() {
        return HEADER_STRING;
    }

    public void addAuthentication(HttpServletResponse response, Authentication auth) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        DefaultClaims claim = new DefaultClaims();
        claim.setSubject(auth.getName());
        Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        if(!auth.getAuthorities().isEmpty()){
            List<String> authorities = auth.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
            claim.put("authorities",authorities.toArray());
        }



        String JWT = Jwts.builder()
                .setClaims(claim)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        response.setHeader("expires", Long.toString(expiration.getTime()));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        response.addHeader(EXPOSE_HEADERS, EXPOSE_HEADERS_STRING);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        Gson gson = new Gson();

        if (token != null) {
            try {
                Claims claim = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                String user = claim.getSubject();

                List<String> authorities = (List<String>) claim.get("authorities");;
                List<GrantedAuthority> lg;

                if(!CollectionUtils.isEmpty(authorities)){
                    lg = authorities.stream().map((authority) -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
                }else{
                    lg = Collections.emptyList();
                }
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, lg);
                }
            }catch (Exception e){
                LOG.error("Get authorization Error",e);
            }
        }
        return null;
    }
}

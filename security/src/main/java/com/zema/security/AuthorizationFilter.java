package com.zema.security;

import com.zema.clients.user.User;
import com.zema.commons.security.SecurityConstants;
import com.zema.commons.validations.ValidationUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {


    AuthenticationManager authenticationManager;

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("doFilterInternal method called");
        //get the authorization header
        String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);
        if (authorizationHeader == null) {
            chain.doFilter(request, response);
            return;
        }
        //get the token
        String jwt = getJwtFromHeader(authorizationHeader);
        if (jwt == null) {
            chain.doFilter(request, response);
            return;
        }
        //get the token claims
        /**Claims claims=SecurityUtils.getTokenClaims(jwt);*/
        Claims claims = Jwts.parserBuilder()  // (1)
                .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.SECURITY_KEY.getBytes())) // (2)
                .build()// (3)
                .parseClaimsJws(jwt).getBody();
        var userId = claims.getSubject();
        if (!ValidationUtils.validId(userId)) {
            chain.doFilter(request, response);
            return;
        }
        //INFO: call user service to get user details
        RestTemplate restTemplate = new RestTemplate();
        var user = restTemplate.getForObject(SecurityConstants.GET_USER_INTERNALLY_BASE_PATH +"/"+ userId, User.class);
        //TODO: add authorities
        List<? extends GrantedAuthority> authorities = new ArrayList<>();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request, response);
    }

    private String getJwtFromHeader(String authorizationHeader) {
        //extract token
        if (authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "");
        }
        return null;
    }
}

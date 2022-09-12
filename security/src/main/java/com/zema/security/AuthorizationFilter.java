package com.zema.security;

import com.zema.commons.exceptions.AppException;
import com.zema.commons.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {


    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
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
        //System.out.println("jwt: "+jwt);
        //  System.out.println("key "+Keys.hmacShaKeyFor(SecurityConstants.SECURITY_KEY.getBytes()));
        if (jwt == null) {
            chain.doFilter(request, response);
            return;
        }
        //get the token claims
        /**Claims claims=SecurityUtils.getTokenClaims(jwt);*/
        Claims claims = Jwts.parserBuilder()  // (1)
                .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.SECURITY_KEY.getBytes()))   // (2)
                .build()// (3)
                .parseClaimsJws(jwt).getBody();
        // System.out.println("claims: "+claims);
        // get username (email)
        String username = claims.getSubject();
        // System.out.println("username: "+username);
        // get user
//        var user = userService.loadUserByUsername(username);
//        // System.out.println("user: "+user);
//        if (user == null) {
//            chain.doFilter(request, response);
//            return;
//        }
        //UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        //SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
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

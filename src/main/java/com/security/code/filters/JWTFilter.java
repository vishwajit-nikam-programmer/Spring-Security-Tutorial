package com.security.code.filters;

import com.security.code.serviceImpl.JWTServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Configuration
public class JWTFilter extends OncePerRequestFilter {

    private final JWTServiceImpl jwtServiceImpl;
    public JWTFilter(JWTServiceImpl jwtServiceImpl) {
        this.jwtServiceImpl = jwtServiceImpl;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if(authHeader != null &&authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
        }

        if(token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Claims claims = jwtServiceImpl.verifySignatureAndExtractAllClaims(token);
            if (!jwtServiceImpl.isTokenExpired(token)) {
                   //if token is not expired then I need to set that
                  // token into SecurityContextHolder "SecurityContextHolder.getContext().getAuthentication()"
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(claims.getSubject(),null,new ArrayList<>());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}

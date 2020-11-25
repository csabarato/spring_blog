package com.csaba.blog.spring_blog.filters;

import com.csaba.blog.spring_blog.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    private JwtService jwtService;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.split(" ")[1];
            try {
                username = jwtService.extractUsername(jwt);
            } catch (MalformedJwtException e) {
                response.sendError(405, "Error: JWT token is malformed");
                return;
            } catch (ExpiredJwtException e) {
                response.sendError(405, "Error: JWT token is expired");
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                response.sendError(405, "Error: Invalid JWT token");
                return;
            }
        } else {
            response.sendError(405, "Unauthorized");
            return;
        }
    filterChain.doFilter(request, response);
    }
}
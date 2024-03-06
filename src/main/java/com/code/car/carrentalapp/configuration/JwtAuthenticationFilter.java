package com.code.car.carrentalapp.configuration;

import com.code.car.carrentalapp.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull  HttpServletResponse response,
            @NonNull  FilterChain filterChain) throws ServletException, IOException {
        // Capture  the authorization header to remove bearer token
        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null  || !authHeader.startsWith("Bearer ")){
            //Continue with the request
            filterChain.doFilter(request, response);
            return;
        }


        //Get the bearerToken from the authorization Header
        final String bearerToken = authHeader.substring(7);

        //Extract the username from the authorization header
        final String userEmail = jwtUtil.getUsername(bearerToken);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Retrieving the user credentials from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(jwtUtil.isTokenValid(bearerToken, userDetails)){
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request,response);
    }
}

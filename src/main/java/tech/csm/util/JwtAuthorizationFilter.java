package tech.csm.util;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import tech.csm.security.JwtUserDetails;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = resolveToken(request);

            if (token != null) {
                Claims claims = jwtUtil.extractClaims(token);

                if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    String username = claims.getSubject();

                    // Handle single or multiple roles
                    Object roleClaim = claims.get("role");
                    List<SimpleGrantedAuthority> authorities;

                    if (roleClaim instanceof String) {
                        String role = ((String) roleClaim).trim();
                        if (!role.isEmpty()) {
                            authorities = Collections.singletonList(
                                    new SimpleGrantedAuthority(role.toUpperCase()));
                        } else {
                            // Default to RECEPTIONIST if role is empty
                            authorities = Collections.singletonList(
                                    new SimpleGrantedAuthority("RECEPTIONIST"));
                        }
                    } else if (roleClaim instanceof List) {
                        authorities = ((List<?>) roleClaim).stream()
                                .filter(r -> r instanceof String)
                                .map(r -> ((String) r).trim().toUpperCase())
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());
                    } else {
                        // Default to RECEPTIONIST if role is null or missing
                        authorities = Collections.singletonList(
                                new SimpleGrantedAuthority("RECEPTIONIST"));
                    }

                    // Wrap claims into JwtUserDetails
                    JwtUserDetails userDetails = new JwtUserDetails();
                    userDetails.setId((String) claims.get("userId"));
                    userDetails.setUsername(username);
                    userDetails.setAuthorities(authorities);

                    // Set authentication
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            logger.error("JWT authentication failed: {}", e.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
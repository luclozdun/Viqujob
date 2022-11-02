package com.viqujob.jobagapi.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.viqujob.jobagapi.exception.ResourceNotFoundException;
import com.viqujob.jobagapi.security.domain.service.AuthenticateService;
import com.viqujob.jobagapi.security.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
    // Documentar si es necesario caso
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticateService authenticateService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String token = null;
        String role = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            var data = jwtUtil.getAllClaimsFromToken(token);
            email = data.getHeaderClaim("sub").toString();
            role = data.getHeaderClaim("role").toString();
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            if (role.equals("EMPLOYER")) {
                authorities.add(new SimpleGrantedAuthority("EMPLOYER"));
                var employer = authenticateService.authenticateEmployerFilter(email);
                userDetails = new User(employer.getEmail(), employer.getPassword(), authorities);
            } else if (role.equals("POSTULANT")) {
                authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
                var postulant = authenticateService.authenticatePostulantFilter(email);
                userDetails = new User(postulant.getEmail(), postulant.getPassword(), authorities);
            } else {
                throw new ResourceNotFoundException("Error ocurred while Filter Internal");
            }

            if (jwtUtil.validToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}

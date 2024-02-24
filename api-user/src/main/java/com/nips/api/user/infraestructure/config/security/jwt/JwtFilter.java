package com.nips.api.user.infraestructure.config.security.jwt;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nips.api.user.application.service.UserSecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.nips.api.user.common.RouteMapping.PUBLIC_API;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserSecurityService userSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwtHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String requestURI = request.getRequestURI();
            if (StringUtils.isEmpty(jwtHeader) || requestURI.contains(PUBLIC_API)){
                filterChain.doFilter(request, response);
                return;
            }

            if( !jwtHeader.startsWith("Bearer ")) {
                throw new JWTVerificationException("Invalid Authorization header");
            }

            this.jwtUtils.verifyToken(jwtHeader);

            String username = this.jwtUtils.getUserToken(jwtHeader);
            UserDetails user = userSecurityService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword(), user.getAuthorities());

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            log.error("Error: ", e);
        }

        filterChain.doFilter(request, response);

    }

}
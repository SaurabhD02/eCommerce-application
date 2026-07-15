package com.flipkart.authservice.security.filter;

import com.flipkart.authservice.entity.CustomUserDetails;
import com.flipkart.authservice.service.impl.CustomUserDetailsService;
import com.flipkart.authservice.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private CustomUserDetailsService customUserDetailsService;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String mobileNo = null;
            String token = getJwtFromHeader(request);
            if(null != token){
                mobileNo = jwtUtil.extractUserName(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(mobileNo);
                if(userDetails !=null) {
                    if(jwtUtil.validateToken(token, userDetails)) {
                        if(null != userDetails.getUsername() && SecurityContextHolder.getContext().getAuthentication() == null) {
                            //set the authentication
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            }else {
                log.error("Authorization Header is missing");
            }
        } catch (Exception e) {
            log.info("JWT Filter Exception");
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        String token = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            token = authorizationHeader.substring(BEARER_PREFIX.length());
        }else{
            log.info("Authorization header not found");
        }
        return token;
    }
}

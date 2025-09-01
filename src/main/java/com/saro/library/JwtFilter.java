package com.saro.library;

import com.saro.library.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();

        // Skip Swagger/OpenAPI endpoints
        if (path.startsWith("/swagger-ui/") || path.startsWith("/v3/api-docs/") || path.startsWith("/swagger-resources/") || path.startsWith("/webjars/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeaders = request.getHeader("Authorization");

        if(authHeaders != null && authHeaders.startsWith("Bearer ")){
            String token = authHeaders.substring(7);

            if(jwtUtil.verifyToken(token)){
                String email = jwtUtil.extractEmail(token);

                var auth = new UsernamePasswordAuthenticationToken(email, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request,response);

    }
}

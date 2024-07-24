package com.haruittl.parking.security;

import com.haruittl.parking.service.UserDetailsServiceImpl;
import com.haruittl.parking.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2

public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    log.info("Processing request {}", request.getRequestURI());
    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtil.validateToken(jwt)) {
        String username = jwtUtil.extractUsername(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(
            username);  // UserService 대신 UserDetailsServiceImpl 사용
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      log.error("Cannot set user authentication: {}", e);
    }
    log.info("여기까진왔냐??");
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    log.info("Header: {}", headerAuth);

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      log.info("Extracted JWT: {}", headerAuth.substring(7));
      return headerAuth.substring(7);
    }
    log.info("No JWT found");
    return null;
  }
}
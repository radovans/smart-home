package cz.sinko.smarthome.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import cz.sinko.smarthome.repository.entities.User;
import cz.sinko.smarthome.web.rest.exceptions.ResourceNotFoundException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final String tokenHeader;

	@Autowired
	public JwtAuthenticationFilter(JwtService jwtService, @Value("${jwt.header}") String tokenHeader) {
		this.jwtService = jwtService;
		this.tokenHeader = tokenHeader;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		String token = httpServletRequest.getHeader(tokenHeader);
		logger.debug("auth header content: " + token);
		if (token != null) {
			try {
				User user = jwtService.parseToken(token);
				if (user != null) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
					logger.info("authenticated user " + user.getUsername() + " setting security context");
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (ResourceNotFoundException ex) {
				httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
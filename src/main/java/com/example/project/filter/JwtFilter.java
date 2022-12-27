package com.example.project.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.project.Service.UserService;
import com.example.project.utility.JWTUtility;

@Component
public class JwtFilter extends OncePerRequestFilter{

	 @Autowired
	    private JWTUtility jwtUtility;

	    @Autowired
	    private UserService userService;

	    @Override
	    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
	        String authorization = httpServletRequest.getHeader("Authorization");
	        String token = null;
	        String userName = null;

	     // JWT Token is in the form "Bearer token". Remove Bearer word and get
			// only the Token
			
	        
	        if(null != authorization && authorization.startsWith("Bearer ")) {
	            token = authorization.substring(7);
	            userName = jwtUtility.getUsernameFromToken(token);
	        }

	        if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails
	                    = userService.loadUserByUsername(userName);

	            if(jwtUtility.validateToken(token,userDetails)) {
	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
	                        = new UsernamePasswordAuthenticationToken(userDetails,
	                        null, userDetails.getAuthorities());

	                usernamePasswordAuthenticationToken.setDetails(
	                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
	                );
	            	// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.

	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }

	        }
	        filterChain.doFilter(httpServletRequest, httpServletResponse);
	    }
}

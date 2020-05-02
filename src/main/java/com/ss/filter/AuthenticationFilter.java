package com.ss.filter;


import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ss.service.impl.UserService;
import com.ss.util.FINCRJwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
    @Autowired
    private UserService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String jwtToken = httpServletRequest.getHeader("Authorization");
        String userName = null;
        if(Objects.nonNull(jwtToken))
        {
        	try {
        		userName = FINCRJwtUtil.extractUserName(jwtToken);
                if(Objects.nonNull(userName) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication()))
                {
                	UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

                    if(FINCRJwtUtil.validateToken(jwtToken,userDetails))
                    {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
        	}
        	catch(ExpiredJwtException jwtException)
        	{
        		logger.info("Token expired, please login again." + jwtException);
        	}
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}

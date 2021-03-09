package com.codef.api.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.codef.api.dto.ResultDto;
import com.codef.api.exception.ErrorCode;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtProvider jwtProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String token = jwtProvider.resolveToken((HttpServletRequest) request);
		try {
		if (token != null && jwtProvider.validateToken(token)) {
			Authentication authentication = jwtProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		} catch (ExpiredJwtException e) {
			
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			
			httpResponse.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
		} catch (SignatureException e) {
			
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} 
		filterChain.doFilter(request, response);
	}

}

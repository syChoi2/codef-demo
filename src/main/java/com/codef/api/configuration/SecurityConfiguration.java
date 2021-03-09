package com.codef.api.configuration;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.codef.api.dto.ResponseDto;
import com.codef.api.dto.ResultDto;
import com.codef.api.enums.Role;
import com.codef.api.exception.BusinessException;
import com.codef.api.exception.ErrorCode;
import com.codef.api.jwt.JwtAuthenticationFilter;
import com.codef.api.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtProvider jwtProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private String[] AUTH_WHITELIST = {
            "/webjars/**", "/configuration/**", "/", "/error", "/csrf"
            , "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/swagger", "/h2/**", "/h2-console/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
            .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
            .and()
            .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
            .antMatchers(HttpMethod.POST, "/users").permitAll()
            .antMatchers("/auth/token").permitAll()
            .antMatchers("/v1/**").hasRole(Role.USER.name())
            .anyRequest().permitAll()// 그외 나머지 요청은 모두 인증된 회원만 접근 가능.and()
    		.and()
    		.exceptionHandling()
		    .authenticationEntryPoint((request, response, e) -> {


		        response.setContentType("application/json;charset=UTF-8");
		        
				
				int httpStatus = response.getStatus();
				String errorCode = "";
				String message = "";
				BusinessException ee = null;
				if( httpStatus == HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION) {

					ee = new BusinessException(ErrorCode.ACCESS_TOKEN_EXPIRED);
					log.error("SecurityAuthenticationException", ee);
			        errorCode = ErrorCode.ACCESS_TOKEN_EXPIRED.getCode();
			        message = ee.getErrorCode().getMessage();
			        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			        
				} else if( httpStatus == HttpServletResponse.SC_UNAUTHORIZED) {

					ee = new BusinessException(ErrorCode.UNAUTHORIZED);
					log.error("SecurityAuthenticationException", ee);
					errorCode = ErrorCode.UNAUTHORIZED.getCode();
					message = ee.getErrorCode().getMessage();
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					
				}
				
				ResultDto resultDto = ResultDto.builder()
						.code(errorCode)
						.message(message)
						.extraMessage("")
						.build();
				
				JSONObject errorResponse = new JSONObject();
				errorResponse.put("result", resultDto);
				errorResponse.put("data", null);
				response.getWriter().write(errorResponse.toString());
		        
		        
		    })
            ;

        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers(AUTH_WHITELIST);
    }
}

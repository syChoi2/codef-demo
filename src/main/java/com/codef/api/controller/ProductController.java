package com.codef.api.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codef.api.dto.JwtAuthentication;
import com.codef.api.dto.PathDto;
import com.codef.api.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;

	/**
	 * 
	 * @param pathDto : /configuration/PathVariableMethodArgumentResolver.java 에서 path로 추출해서 생성한 DTO
	 * @param authentication : access token 에서 추출한 user 정보
	 */
	@PostMapping("/{versionCode}/{countryCode}/{jobCode}/{customerCode}/{productCode}")
	public JSONObject apiCall(PathDto pathDto, @RequestBody HashMap<String, Object> paramMap, @AuthenticationPrincipal JwtAuthentication authentication) throws JsonProcessingException, UnsupportedEncodingException, ParseException{
		
		JSONObject json = productService.apiCall(authentication.userId, pathDto, paramMap);
		
		return json;
	} 
	
	
	
}

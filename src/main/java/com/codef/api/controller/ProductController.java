package com.codef.api.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codef.api.dto.JwtAuthentication;
import com.codef.api.dto.PathDto;
import com.codef.api.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Api(tags = "product-controller", description = "상품 호출 API")
public class ProductController {
	
	private final ProductService productService;

	/**
	 * path ex) /v1/kr/bank/p/accounts
	 * 
	 * @param pathDto : /configuration/PathVariableMethodArgumentResolver.java 에서 path로 추출해서 생성한 DTO
	 * @param authentication : access token 에서 추출한 user 정보
	 */
	@ApiOperation(value="API 호출")
	//@ApiImplicitParam(name = "x-access-token", value = "토큰", required = true, dataType = "String", paramType = "header")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization",paramType = "header", value = "토큰", required = true, example = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwidXNlcklkIjoiOSIsImlzcyI6ImNvZGVmX2RlbW8iLCJpYXQiOjE2MTUyNzMyNjl9.8I-tykRK0dizNz0eUwlzeSPDIfQBHCB7ZF_Cj5ggrUMbFU820d9f1kM2iJhwc4ota7oPpQ4aDWLDOgBX_qO4xg")
	})
	@PostMapping("/{versionCode}/{countryCode}/{jobCode}/{customerCode}/{productCode}")
	public JSONObject apiCall(
			  @PathVariable("versionCode") @ApiParam(example = "v1", value = "버젼코드", required = true)  String versionName
			, @PathVariable("countryCode")  @ApiParam(example = "kr", value = "국가코드", required = true) String countryName
			, @PathVariable("jobCode")  @ApiParam(example = "bank", value = "업무코드", required = true) String jobName
			, @PathVariable("customerCode")  @ApiParam(example = "p", value = "고객구분코드", required = true) String customerName
			, @PathVariable("productCode")  @ApiParam(example = "accounts", value = "상품코드", required = true) String productName
			, @ApiIgnore PathDto pathDto
			, @RequestBody @ApiParam(value = "상품에 필요한 param\r\n ex)\r\n { clientId:\"\", clientSecret:\"\" }") HashMap<String, Object> paramMap
			, @AuthenticationPrincipal JwtAuthentication authentication) throws JsonProcessingException, UnsupportedEncodingException, ParseException{
		
		JSONObject json = productService.apiCall(authentication.userId, pathDto, paramMap);
		
		return json;
	} 
	
	
	
}

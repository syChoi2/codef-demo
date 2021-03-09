package com.codef.api.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codef.api.dto.ClientInfoDto;
import com.codef.api.dto.UserDto;
import com.codef.api.dto.UserInfoDto;
import com.codef.api.service.AuthenticationService;
import com.codef.api.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(tags = "ect-controller", description = "회원가입, access token 발급 api")
public class EctController {

	private final AuthenticationService authenticationService;

	private final UserService userService;
	
	/**
	 * 회원가입 + 클라이언트 ID, SECRET 발급
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@ApiOperation(value="회원가입 + 클라이언트 ID, SECRET 발급")
    @PostMapping("/users")
    public UserDto createUser(@RequestBody @ApiParam(value = "회원가입에 필요한 param", required =true) UserInfoDto userInfoDto) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	
    	UserDto userDto = userService.join(userInfoDto);

        return userDto;
    }
    
	
	/**
	 * client id, secret 으로 access token 발급
	 */
	@ApiOperation(value="client id, secret 으로 access token 발급")
	@PostMapping("/auth/token")
	public String getAccessToken(@RequestBody @ApiParam(value = "access token에 필요한 param", required =true) ClientInfoDto authTokenRequestDto) {
		
		String accessToken = authenticationService.getAccessToken(authTokenRequestDto);
		
		return accessToken;
	}
}

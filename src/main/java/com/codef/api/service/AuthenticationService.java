package com.codef.api.service;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.codef.api.dto.ClientInfoDto;
import com.codef.api.entity.Users;
import com.codef.api.enums.Role;
import com.codef.api.exception.BusinessException;
import com.codef.api.exception.ErrorCode;
import com.codef.api.jwt.JwtProvider;
import com.codef.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;

    private final JwtProvider jwtProvider;
    
	public String getAccessToken(ClientInfoDto authTokenRequestDto) {

		// 0. user 정보 
		Users user = userRepository.findByClientIdAndClientSecret(authTokenRequestDto.getClientId(), authTokenRequestDto.getClientSecret()).orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));
		
		// 2. access token 발급
        String token = jwtProvider.createToken(String.valueOf(user.getUserId()), Collections.singletonList(Role.USER.value()));

		// 3. return
		return token;
	}

}

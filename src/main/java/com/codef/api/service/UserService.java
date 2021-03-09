package com.codef.api.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codef.api.dto.UserDto;
import com.codef.api.dto.UserInfoDto;
import com.codef.api.entity.Users;
import com.codef.api.exception.BusinessException;
import com.codef.api.exception.ErrorCode;
import com.codef.api.repository.UserRepository;
import com.codef.api.util.BytesToHex;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;

	public UserDto join(UserInfoDto userInfoDto) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		// 1. 값 검증
		String emailRegEx = "[A-Z0-9a-z.-_]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}";
		if(!userInfoDto.getEmail().matches(emailRegEx)) {
			throw new BusinessException("올바른 이메일 형식을 사용하세요.", ErrorCode.INVALID_FORMAT);
		}

		checkNotNull(userInfoDto.getPassword(), "비밀번호를 입력하세요.");
		checkArgument(userInfoDto.getPassword().length() >= 8 && userInfoDto.getPassword().length() <= 15, "8 < 비밀번호 길이 <= 15" );
		
        
        // 2. client id, secret 발급
        String clientId = UUID.randomUUID().toString();
        
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        String digest = BytesToHex.bytesToHex(salt.digest());
        
        String clientSecret = digest;
        
        // 3. 유저 생성
        Users newUser = Users.builder()
                .email(userInfoDto.getEmail())
                .password(passwordEncoder.encode(userInfoDto.getPassword()))
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
        Users user = userRepository.save(newUser);

        // 4. 클라이언트 정보 return 
        // TODO: 실제의 경우 web에서 로그인 session 을 생성하여 로그인을 유지하고 해당 session 의 정보로 web의 요청 권한을 체크한다. 
        return(UserDto.builder().userId(user.getUserId()).clientId(clientId).clientSecret(clientSecret).build());  
	}


    public Optional<Users> findById(Long userId) {
        return userRepository.findById(userId);
    }


}

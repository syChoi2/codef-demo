package com.codef.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

	private Long userId;
	private String clientId;
	private String clientSecret;
}

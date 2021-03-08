package com.codef.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientInfoDto {

    public ClientInfoDto() {
        super();
    }

	public ClientInfoDto(String clientId, String clientSecret) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	private String clientId;
	private String clientSecret;
}

package com.codef.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResultDto {

	private String code;
	private String message;
	private String extraMessage;
//	private String transactionId;
	
}

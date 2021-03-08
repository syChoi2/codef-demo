package com.codef.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto<T> {

	private ResultDto result;
	
	private T data;
}

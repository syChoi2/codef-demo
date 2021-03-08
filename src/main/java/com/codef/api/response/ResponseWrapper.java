package com.codef.api.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.codef.api.dto.ResponseDto;
import com.codef.api.dto.ResultDto;
import com.codef.api.exception.ErrorResponse;

@RestControllerAdvice("com.codef.api")
public class ResponseWrapper implements ResponseBodyAdvice<Object>{

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		

		ResultDto resultDto;
		HttpStatus httpStatus = HttpStatus.OK;
		//exception(error)의 경우
		if(body instanceof ErrorResponse) {

			ErrorResponse errorResponse = (ErrorResponse) body;
			
			resultDto = ResultDto.builder()
					.code(errorResponse.getCode())
					.message(errorResponse.getMessage())
					.extraMessage("")
					.build();
			body = null;
			httpStatus = HttpStatus.BAD_REQUEST;
			response.setStatusCode(httpStatus);
			
		}else {
			
			resultDto = ResultDto.builder()
					.code("CM000")
					.message("SUCCESS")
					.extraMessage("")
					.build();
		}
		
		ResponseDto<?> responseDto = ResponseDto.builder()
				.result(resultDto)
				.data(body)
				.build();
		
		return new ResponseEntity<>(responseDto, httpStatus).getBody();
	}
	
}

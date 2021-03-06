package com.codef.api.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum ErrorCode {

    // common
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "CM001", "일시적인 서버 오류"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "CM002", "권한 없음"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CM003", "요청 결과를 찾을 수 없습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "CM004", "잘못된 요청입니다."),
    INSUFFICIENT_VALUE(HttpStatus.BAD_REQUEST.value(), "CM005", "요청값이 부족합니다."),
    INVALID_FORMAT(HttpStatus.BAD_REQUEST.value(), "CM006", "잘못된 형식입니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST.value(), "CM007", "접근 권한이 만료되었습니다."),

    // User Controller
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "US001", "사용자를 찾을 수 없습니다."),
	
	// Letter Controller
    PLATFORM_NOT_SUBSCRIBING(HttpStatus.BAD_REQUEST.value(), "PL001", "구독하고 있지 않은 플랫폼입니다.");


    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
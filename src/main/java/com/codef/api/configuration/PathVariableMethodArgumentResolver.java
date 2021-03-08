package com.codef.api.configuration;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.codef.api.dto.PathDto;

public class PathVariableMethodArgumentResolver implements HandlerMethodArgumentResolver {

//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        Class<?> parameterType = parameter.getParameterType();
//        return PathDto.class.equals(parameterType);
//    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
      return parameter.getParameter().getType() == PathDto.class;
    }
    
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String requestPath = ((ServletWebRequest) webRequest)
          .getRequest()
          .getRequestURI();
        // /versionCode/countryCode/jobCode/customerCode/productCode

        String[] pathVariables = requestPath.split("/");
        
        PathDto pathDto = PathDto.builder()
        		.versionName(pathVariables[1])
        		.countryName(pathVariables[2])
        		.jobName(pathVariables[3])
        		.customerName(pathVariables[4])
        		.productName(pathVariables[5])
        		.build();
        return pathDto;
    	
    }
}
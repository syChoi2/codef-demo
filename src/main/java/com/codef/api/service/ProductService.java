package com.codef.api.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Optional;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.codef.api.dto.PathDto;
import com.codef.api.entity.ProductParameters;
import com.codef.api.entity.Products;
import com.codef.api.entity.UserProductRegister;
import com.codef.api.exception.BusinessException;
import com.codef.api.exception.ErrorCode;
import com.codef.api.repository.ProductParameterRepository;
import com.codef.api.repository.ProductRepository;
import com.codef.api.repository.UserProductRegisterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

	private final ProductParameterRepository productParameterRepository;
	private final ProductRepository productRepository;
	private final UserProductRegisterRepository userProductRegisterRepository;
	private final ObjectMapper mapper;

	public JSONObject apiCall(Long userId, PathDto pathDto, HashMap<String, Object> paramMap) throws JsonProcessingException, UnsupportedEncodingException, ParseException {
		
		
		// 0. 상품 검색
		Products product = productRepository.findProduct(pathDto).orElseThrow(()-> new BusinessException("상품 없음", ErrorCode.NOT_FOUND));
		
		// 1. 고객의 client id 로 해당 product가 등록되어 유효한지 확인
		Optional<UserProductRegister> optionalUserProductRegister = userProductRegisterRepository.findUserProductRegister(userId, product.getProductId());
		
		if(optionalUserProductRegister.isEmpty()) {
			throw new BusinessException("상품 등록 정보 없음", ErrorCode.NOT_FOUND);
		}
		
		// 2. parameter확인
		// 2-1. parameter 호출
		Iterable<ProductParameters> iterableProductParameters = productParameterRepository.findProductParameters(product.getProductId());
		
		for (ProductParameters productParameters : iterableProductParameters) {
			if ( paramMap.get(productParameters.getParameterName()) == null ){
				throw new BusinessException(ErrorCode.INSUFFICIENT_VALUE);
			}
		}
		
		// 2-2. parameter 세팅
		String bodyString = mapper.writeValueAsString(paramMap);	
		bodyString = URLEncoder.encode(bodyString, "UTF-8");
		
		
		// 3. api call
		// 3-1. API 요청
		//TODO: 외부 API call
		//JSONObject json = (JSONObject) HttpRequest.post(product.getApiAddress(), "REAL_API_TOKEN", bodyString);	//상품(업무/기관/고객)별 api별 토큰을 DB로 관리하여야한다.	

		//TODO: return json 분석하여 exception 캐치 처리

		// 4. 임시 결과
		String tempResultStr = "{ " + 
				"   \"resDepositTrust\":[], " + 
				"   \"resForeignCurrency\":[], " + 
				"   \"resFund\":[], " + 
				"   \"resLoan\":[], " + 
				"   \"resInsurance\":[] " + 
				"}";

		JSONParser parser = new JSONParser();
		Object obj = parser.parse(tempResultStr);
		JSONObject jsonObj = (JSONObject) obj;

		return jsonObj;
	}
	
	

}

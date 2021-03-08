package com.codef.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PathDto {

	private String versionName;
	private String countryName;
	private String jobName;
	private String customerName;
	private String productName;
}

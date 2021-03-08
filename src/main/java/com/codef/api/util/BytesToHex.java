package com.codef.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BytesToHex {
	public String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}
}

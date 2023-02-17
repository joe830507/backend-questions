package com.common;

import lombok.Data;

@Data
public class ApiResult {

	private Code code;
	private Object data;
	private String message;

	private ApiResult(Code code, Object data) {
		this.code = code;
		this.data = data;
	}

	private ApiResult(Code code, String message) {
		this.code = code;
		this.message = message;
	}

	public static ApiResult ok() {
		return new ApiResult(Code.OK, null);
	}

	public static ApiResult ok(Object data) {
		return new ApiResult(Code.OK, data);
	}

	public static ApiResult error(String message) {
		return new ApiResult(Code.ERROR, message);
	}

	private enum Code {
		OK, ERROR
	}
}

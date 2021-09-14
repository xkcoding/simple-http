package com.xkcoding.http.support;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 响应封装
 * </p>
 *
 * @author zhihai.yu
 * @date Created in 2021/09/10 20:24
 */
public class SimpleHttpResponse {
	private boolean success;
	private int code;
	private Map<String, List<String>> headers;
	private String body;

	public SimpleHttpResponse() {
	}

	public SimpleHttpResponse(boolean success, int code, Map<String, List<String>> headers, String body) {
		this.success = success;
		this.code = code;
		this.headers = headers;
		this.body = body;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public SimpleHttpResponse setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
		return this;
	}

	public String getBody() {
		return body;
	}

	public SimpleHttpResponse setBody(String body) {
		this.body = body;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public SimpleHttpResponse setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public int getCode() {
		return code;
	}

	public SimpleHttpResponse setCode(int code) {
		this.code = code;
		return this;
	}

	@Override
	public String toString() {
		return "SimpleHttpResponse{" +
			"success=" + success +
			", code=" + code +
			", headers=" + headers +
			", body='" + body + '\'' +
			'}';
	}
}

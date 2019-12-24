package com.xkcoding.http.support;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 请求头封装
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 18:24
 */
public class HttpHeader {
	private final Map<String, String> headers;

	public HttpHeader() {
		this.headers = new HashMap<>(16);
	}

	public HttpHeader(Map<String, String> headers) {
		this.headers = headers;
	}

	public HttpHeader add(String key, String value) {
		this.headers.put(key, value);
		return this;
	}

	public HttpHeader addAll(Map<String, String> headers) {
		this.headers.putAll(headers);
		return this;
	}
}

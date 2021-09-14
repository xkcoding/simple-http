package com.xkcoding.http.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleHttpResponse {
	private boolean success;
	private int code;
	private Map<String, List<String>> headers;
	private String body;
}

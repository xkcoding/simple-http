package com.xkcoding.http.config;

import com.xkcoding.http.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.Proxy;

/**
 * <p>
 * Http 配置类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020-04-29 14:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpConfig {
	/**
	 * 超时时长，单位毫秒
	 */
	private int timeout = Constants.DEFAULT_TIMEOUT;
	/**
	 * 代理配置
	 */
	private Proxy proxy;
}

package com.xkcoding.http.support.java11;

import com.xkcoding.http.config.HttpConfig;
import com.xkcoding.http.exception.SimpleHttpException;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 默认代理选择器
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020-04-29 15:59
 */
public class DefaultProxySelector extends ProxySelector {
	private HttpConfig httpConfig;

	public DefaultProxySelector(HttpConfig httpConfig) {
		this.httpConfig = httpConfig;
	}

	@Override
	public List<Proxy> select(URI uri) {
		return Collections.singletonList(httpConfig.getProxy());
	}

	@Override
	public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
		throw new SimpleHttpException("Proxy connect failed!");
	}
}

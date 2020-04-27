/*
 * Copyright (c) 2019-2029, xkcoding & Yangkai.Shen & 沈扬凯 (237497819@qq.com & xkcoding.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xkcoding.http;

import com.xkcoding.http.exception.SimpleHttpException;
import com.xkcoding.http.support.Http;
import com.xkcoding.http.support.HttpHeader;
import com.xkcoding.http.support.httpclient.HttpClientImpl;
import com.xkcoding.http.support.hutool.HutoolImpl;
import com.xkcoding.http.support.okhttp3.OkHttp3Impl;
import com.xkcoding.http.util.ClassUtil;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * <p>
 * 请求工具类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 18:15
 */
@UtilityClass
public class HttpUtil {
	private static Http proxy;

	static {
		Http defaultProxy = null;
		ClassLoader classLoader = HttpUtil.class.getClassLoader();
		// 基于 java 11 HttpClient
		if (ClassUtil.isPresent("java.net.http.HttpClient", classLoader)) {
			try {
				defaultProxy = new com.xkcoding.http.support.java11.HttpClientImpl();
			}catch (Throwable e){}

		}
		// 基于 okhttp3
		if (ClassUtil.isPresent("okhttp3.OkHttpClient", classLoader) && defaultProxy == null) {
			try {
				defaultProxy = new OkHttp3Impl();
			}catch (Throwable e){}
		}
		// 基于 httpclient
		if (ClassUtil.isPresent("org.apache.http.impl.client.HttpClients", classLoader) && defaultProxy == null) {
			try {
				defaultProxy = new HttpClientImpl();
			}catch (Throwable e){}
		}
		// 基于 hutool
		if (ClassUtil.isPresent("cn.hutool.http.HttpRequest", classLoader) && defaultProxy == null) {
			try {
				defaultProxy = new HutoolImpl();
			}catch (Throwable e){}
		}
		proxy = defaultProxy;
	}

	public void setHttp(Http http) {
		proxy = http;
	}

	private void checkHttpNotNull(Http proxy) {
		if (null == proxy) {
			throw new SimpleHttpException("HTTP 实现类未指定！");
		}
	}

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	public String get(String url) {
		checkHttpNotNull(proxy);
		return proxy.get(url);
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public String get(String url, Map<String, String> params, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.get(url, params, encode);
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public String get(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.get(url, params, header, encode);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	public String post(String url) {
		checkHttpNotNull(proxy);
		return proxy.post(url);
	}

	/**
	 * POST 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	public String post(String url, String data) {
		checkHttpNotNull(proxy);
		return proxy.post(url, data);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 * @param header 请求头
	 * @return 结果
	 */
	public String post(String url, String data, HttpHeader header) {
		checkHttpNotNull(proxy);
		return proxy.post(url, data, header);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public String post(String url, Map<String, String> params, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.post(url, params, encode);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public String post(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.post(url, params, header, encode);
	}
}

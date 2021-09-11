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

package com.xkcoding.http.support.hutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xkcoding.http.exception.SimpleHttpException;
import com.xkcoding.http.support.SimpleHttpResponse;
import com.xkcoding.http.config.HttpConfig;
import com.xkcoding.http.support.AbstractHttp;
import com.xkcoding.http.support.HttpHeader;
import com.xkcoding.http.util.MapUtil;
import com.xkcoding.http.util.StringUtil;
import com.xkcoding.http.util.UrlUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Hutool 实现
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 19:08
 */
public class HutoolImpl extends AbstractHttp {
	public HutoolImpl() {
		this(new HttpConfig());
	}

	public HutoolImpl(HttpConfig httpConfig) {
		super(httpConfig);
	}

	private SimpleHttpResponse exec(HttpRequest request) {
		// 设置超时时长
		request = request.timeout(httpConfig.getTimeout());
		// 设置代理
		if (null != httpConfig.getProxy()) {
			request = request.setProxy(httpConfig.getProxy());
		}

		try (HttpResponse response = request.execute()) {

			int code = response.getStatus();
			boolean successful = response.isOk();
			String body = response.body();
			Map<String, List<String>> headers = response.headers();
			return new SimpleHttpResponse(successful, code, headers, body);
		}catch (RuntimeException e){
			e.printStackTrace();
			return new SimpleHttpResponse(false,400,null,null);
		}
	}

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public SimpleHttpResponse get(String url) {
		return this.get(url, null, false);
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public SimpleHttpResponse get(String url, Map<String, String> params, boolean encode) {
		return this.get(url, params, null, encode);
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
	@Override
	public SimpleHttpResponse get(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		String baseUrl = StringUtil.appendIfNotContain(url, "?", "&");
		url = baseUrl + MapUtil.parseMapToString(params, encode);

		HttpRequest request = HttpRequest.get(url);

		if (header != null) {
			MapUtil.forEach(header.getHeaders(), request::header);
		}

		return exec(request);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public SimpleHttpResponse post(String url) {
		HttpRequest request = HttpRequest.post(url);
		return this.exec(request);
	}

	/**
	 * POST 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	@Override
	public SimpleHttpResponse post(String url, String data) {
		return this.post(url, data, null);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 * @param header 请求头
	 * @return 结果
	 */
	@Override
	public SimpleHttpResponse post(String url, String data, HttpHeader header) {
		HttpRequest request = HttpRequest.post(url);

		if (StringUtil.isNotEmpty(data)) {
			request.body(data);
		}

		if (header != null) {
			MapUtil.forEach(header.getHeaders(), request::header);
		}
		return this.exec(request);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public SimpleHttpResponse post(String url, Map<String, String> params, boolean encode) {
		return this.post(url, params, null, encode);
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
	@Override
	public SimpleHttpResponse post(String url, Map<String, String> params, HttpHeader header, boolean encode) {
		HttpRequest request = HttpRequest.post(url);

		if (encode) {
			MapUtil.forEach(params, (k, v) -> request.form(k, UrlUtil.urlEncode(v)));
		} else {
			MapUtil.forEach(params, request::form);
		}

		if (header != null) {
			MapUtil.forEach(header.getHeaders(), request::header);
		}
		return this.exec(request);
	}
}

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

package com.xkcoding.http.support.okhttp3;

import com.xkcoding.http.support.SimpleHttpResponse;
import com.xkcoding.http.config.HttpConfig;
import com.xkcoding.http.constants.Constants;
import com.xkcoding.http.exception.SimpleHttpException;
import com.xkcoding.http.support.AbstractHttp;
import com.xkcoding.http.support.HttpHeader;
import com.xkcoding.http.util.MapUtil;
import com.xkcoding.http.util.StringUtil;
import okhttp3.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * OkHttp3 实现
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 19:06
 */
public class OkHttp3Impl extends AbstractHttp {
	public static final MediaType CONTENT_TYPE_JSON = MediaType.get(Constants.CONTENT_TYPE_JSON);
	private final OkHttpClient.Builder httpClientBuilder;

	public OkHttp3Impl() {
		this(new HttpConfig());
	}

	public OkHttp3Impl(HttpConfig httpConfig) {
		this(new OkHttpClient().newBuilder(), httpConfig);
	}

	public OkHttp3Impl(OkHttpClient.Builder httpClientBuilder, HttpConfig httpConfig) {
		super(httpConfig);
		this.httpClientBuilder = httpClientBuilder;
	}

	private SimpleHttpResponse exec(Request.Builder requestBuilder) {
		this.addHeader(requestBuilder);
		Request request = requestBuilder.build();

		OkHttpClient httpClient;
		// 设置代理
		if (null != httpConfig.getProxy()) {
			httpClient = httpClientBuilder.connectTimeout(Duration.ofMillis(httpConfig.getTimeout())).writeTimeout(Duration.ofMillis(httpConfig.getTimeout())).readTimeout(Duration.ofMillis(httpConfig.getTimeout())).proxy(httpConfig.getProxy()).build();
		} else {
			httpClient = httpClientBuilder.connectTimeout(Duration.ofMillis(httpConfig.getTimeout())).writeTimeout(Duration.ofMillis(httpConfig.getTimeout())).readTimeout(Duration.ofMillis(httpConfig.getTimeout())).build();
		}

		try (Response response = httpClient.newCall(request).execute()) {

			int code = response.code();
			boolean successful = response.isSuccessful();
			Map<String, List<String>> headers = response.headers().toMultimap();
			String body = response.body().string();
			return new SimpleHttpResponse(successful,code,headers,body);
		} catch (IOException e) {
			e.printStackTrace();
			return new SimpleHttpResponse(false,400,null,null);
		}
	}

	/**
	 * 添加request header
	 *
	 * @param builder Request.Builder
	 */
	private void addHeader(Request.Builder builder) {
		builder.header(Constants.USER_AGENT, Constants.USER_AGENT_DATA);
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
		HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
		if (encode) {
			MapUtil.forEach(params, urlBuilder::addEncodedQueryParameter);
		} else {
			MapUtil.forEach(params, urlBuilder::addQueryParameter);
		}
		HttpUrl httpUrl = urlBuilder.build();

		Request.Builder requestBuilder = new Request.Builder().url(httpUrl);
		if (header != null) {
			MapUtil.forEach(header.getHeaders(), requestBuilder::addHeader);
		}
		requestBuilder = requestBuilder.get();

		return exec(requestBuilder);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public SimpleHttpResponse post(String url) {
		return this.post(url, Constants.EMPTY);
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
		if (StringUtil.isEmpty(data)) {
			data = Constants.EMPTY;
		}
		RequestBody body = RequestBody.create(data, CONTENT_TYPE_JSON);

		Request.Builder requestBuilder = new Request.Builder().url(url);
		if (header != null) {
			MapUtil.forEach(header.getHeaders(), requestBuilder::addHeader);
		}
		requestBuilder = requestBuilder.post(body);

		return exec(requestBuilder);
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
		FormBody.Builder formBuilder = new FormBody.Builder();
		if (encode) {
			MapUtil.forEach(params, formBuilder::addEncoded);
		} else {
			MapUtil.forEach(params, formBuilder::add);
		}
		FormBody body = formBuilder.build();

		Request.Builder requestBuilder = new Request.Builder().url(url);
		if (header != null) {
			MapUtil.forEach(header.getHeaders(), requestBuilder::addHeader);
		}
		requestBuilder = requestBuilder.post(body);

		return exec(requestBuilder);
	}


}

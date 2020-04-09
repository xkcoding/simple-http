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

import com.xkcoding.http.constants.Constants;
import com.xkcoding.http.exception.SimpleHttpException;
import com.xkcoding.http.support.Http;
import com.xkcoding.http.support.HttpHeader;
import com.xkcoding.http.util.MapUtil;
import com.xkcoding.http.util.StringUtil;
import okhttp3.*;
import org.apache.http.HttpHeaders;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

/**
 * <p>
 * OkHttp3 实现
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 19:06
 */
public class OkHttp3Impl implements Http {
	public static final MediaType CONTENT_TYPE_JSON = MediaType.get(Constants.CONTENT_TYPE_JSON);
	private final OkHttpClient httpClient;


	public OkHttp3Impl() {
		this(new OkHttpClient().newBuilder()
			.connectTimeout(Duration.ofMillis(Constants.TIMEOUT))
			.writeTimeout(Duration.ofMillis(Constants.TIMEOUT))
			.readTimeout(Duration.ofMillis(Constants.TIMEOUT))
			.build());
	}

	public OkHttp3Impl(OkHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	private String exec(Request.Builder requestBuilder) {
		this.addHeader(requestBuilder);
		Request request = requestBuilder.build();
		try (Response response = httpClient.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new SimpleHttpException("Unexpected code " + response);
			}

			return response.body().string();
		} catch (IOException e) {
			throw new SimpleHttpException(e);
		}
	}

	/**
	 * 添加request header
	 *
	 * @param builder Request.Builder
	 */
	private void addHeader(Request.Builder builder) {
		builder.header(HttpHeaders.USER_AGENT, Constants.USER_AGENT);
	}

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public String get(String url) {
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
	public String get(String url, Map<String, String> params, boolean encode) {
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
	public String get(String url, Map<String, String> params, HttpHeader header, boolean encode) {
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
	public String post(String url) {
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
	public String post(String url, String data) {
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
	public String post(String url, String data, HttpHeader header) {
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
	public String post(String url, Map<String, String> params, boolean encode) {
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
	public String post(String url, Map<String, String> params, HttpHeader header, boolean encode) {
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

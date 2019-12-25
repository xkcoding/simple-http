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

package com.xkcoding.http.support.httpclient;

import com.xkcoding.http.constants.Constants;
import com.xkcoding.http.support.Http;
import com.xkcoding.http.support.HttpHeader;
import com.xkcoding.http.util.MapUtil;
import com.xkcoding.http.util.StringUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * HttpClient 实现
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/25 09:24
 */
public class HttpClientImpl implements Http {
	private CloseableHttpClient httpClient;

	public HttpClientImpl() {
		this.httpClient = HttpClients.createDefault();
	}

	private String exec(HttpRequestBase request) {
		// 设置超时时长
		request.setConfig(RequestConfig.custom()
			.setConnectTimeout(Constants.TIMEOUT)
			.setSocketTimeout(Constants.TIMEOUT)
			.setConnectionRequestTimeout(Constants.TIMEOUT)
			.build());

		try (CloseableHttpResponse response = this.httpClient.execute(request)) {
			if (!isSuccess(response)) {
				throw new RuntimeException("Unexpected code " + response);
			}

			StringBuffer body = new StringBuffer();
			if (response.getEntity() != null) {
				body.append(EntityUtils.toString(response.getEntity(), Constants.DEFAULT_ENCODING));
			}

			return body.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isSuccess(CloseableHttpResponse response) {
		if (response == null) {
			return false;
		}
		if (response.getStatusLine() == null) {
			return false;
		}
		return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
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
		String baseUrl = StringUtil.appendIfNotContain(url, "?", "&");
		url = baseUrl + MapUtil.parseMapToString(params, encode);

		HttpGet httpGet = new HttpGet(url);

		if (header != null) {
			MapUtil.forEach(header.getHeaders(), httpGet::addHeader);
		}

		return exec(httpGet);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public String post(String url) {
		HttpPost httpPost = new HttpPost(url);
		return this.exec(httpPost);
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
		HttpPost httpPost = new HttpPost(url);

		if (StringUtil.isNotEmpty(data)) {
			StringEntity entity = new StringEntity(data, Constants.DEFAULT_ENCODING);
			entity.setContentEncoding(Constants.DEFAULT_ENCODING.displayName());
			entity.setContentType(Constants.CONTENT_TYPE_JSON);
			httpPost.setEntity(entity);
		}

		if (header != null) {
			MapUtil.forEach(header.getHeaders(), httpPost::addHeader);
		}

		return this.exec(httpPost);
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
		HttpPost httpPost = new HttpPost(url);

		if (MapUtil.isNotEmpty(params)) {
			List<NameValuePair> form = new ArrayList<>();
			MapUtil.forEach(params, (k, v) -> form.add(new BasicNameValuePair(k, v)));
			httpPost.setEntity(new UrlEncodedFormEntity(form, Constants.DEFAULT_ENCODING));
		}

		if (header != null) {
			MapUtil.forEach(header.getHeaders(), httpPost::addHeader);
		}

		return this.exec(httpPost);
	}
}

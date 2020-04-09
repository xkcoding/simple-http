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

package com.xkcoding.http.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 常量池
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 19:39
 */
public interface Constants {
	/**
	 * 超时时长，单位毫秒
	 */
	int TIMEOUT = 3000;

	/**
	 * 编码格式
	 */
	Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

	/**
	 * JSON
	 */
	String CONTENT_TYPE_JSON = "application/json; charset=utf-8";

	/**
	 * Content-Type
	 */
	String CONTENT_TYPE = "Content-Type";

	/**
	 * Content-Encoding
	 */
	String CONTENT_ENCODING = "Content-Encoding";

	/**
	 * 空字符串
	 */
	String EMPTY = "";

	/**
	 * User-Agent
	 */
	String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36 simple-http";
}

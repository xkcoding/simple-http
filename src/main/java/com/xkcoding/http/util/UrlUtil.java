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

package com.xkcoding.http.util;

import cn.hutool.core.exceptions.UtilException;
import com.xkcoding.http.constants.Constants;
import lombok.experimental.UtilityClass;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <p>
 * Url 工具类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 22:27
 */
@UtilityClass
public class UrlUtil {
	/**
	 * 编码
	 *
	 * @param value str
	 * @return encode str
	 */
	public String urlEncode(String value) {
		if (value == null) {
			return "";
		}
		try {
			String encoded = URLEncoder.encode(value, Constants.DEFAULT_ENCODING.displayName());
			return encoded.replace("+", "%20").replace("*", "%2A").replace("~", "%7E").replace("/", "%2F");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Failed To Encode Uri", e);
		}
	}

	/**
	 * 解码URL<br>
	 * 将%开头的16进制表示的内容解码。
	 *
	 * @param url URL
	 * @return 解码后的URL
	 * @throws UtilException UnsupportedEncodingException
	 */
	public String urlDecode(String url) throws UtilException {
		if (StringUtil.isEmpty(url)) {
			return url;
		}
		try {
			return URLDecoder.decode(url, Constants.DEFAULT_ENCODING.displayName());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported encoding", e);
		}
	}
}

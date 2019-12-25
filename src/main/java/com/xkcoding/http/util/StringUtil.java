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

import lombok.experimental.UtilityClass;

/**
 * <p>
 * 字符串工具类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 22:51
 */
@UtilityClass
public class StringUtil {

	public static boolean isEmpty(String str) {
		return null == str || str.trim().isEmpty();
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 如果给定字符串{@code str}中不包含{@code appendStr}，则在{@code str}后追加{@code appendStr}；
	 * 如果已包含{@code appendStr}，则在{@code str}后追加{@code otherwise}
	 *
	 * @param str       给定的字符串
	 * @param appendStr 需要追加的内容
	 * @param otherwise 当{@code appendStr}不满足时追加到{@code str}后的内容
	 * @return 追加后的字符串
	 */
	public String appendIfNotContain(String str, String appendStr, String otherwise) {
		if (isEmpty(str) || isEmpty(appendStr)) {
			return str;
		}
		if (str.contains(appendStr)) {
			return str.concat(otherwise);
		}
		return str.concat(appendStr);
	}

}

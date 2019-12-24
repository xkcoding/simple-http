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

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * <p>
 * Map 工具类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 21:07
 */
@UtilityClass
public class MapUtil {
	/**
	 * 判断 map 不为空
	 *
	 * @param map map
	 * @return {@code true} - 不为空，{@code false} - 空
	 */
	public boolean isNotEmpty(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}

	/**
	 * 判断 map 为空
	 *
	 * @param map map
	 * @return {@code true} - 空，{@code false} - 不为空
	 */
	public boolean isEmpty(Map<?, ?> map) {
		return !isNotEmpty(map);
	}

	/**
	 * 遍历
	 *
	 * @param map    待遍历的 map
	 * @param action 操作
	 * @param <K>    map键泛型
	 * @param <V>    map值泛型
	 */
	public <K, V> void forEach(Map<K, V> map, BiConsumer<? super K, ? super V> action) {
		if (isEmpty(map) || action == null) {
			return;
		}
		for (Map.Entry<K, V> entry : map.entrySet()) {
			K k;
			V v;
			try {
				k = entry.getKey();
				v = entry.getValue();
			} catch (IllegalStateException ise) {
				// this usually means the entry is no longer in the map.
				throw new ConcurrentModificationException(ise);
			}
			action.accept(k, v);
		}
	}

	/**
	 * map转字符串，转换后的字符串格式为 {@code xxx=xxx&xxx=xxx}
	 *
	 * @param params 待转换的map
	 * @param encode 是否转码
	 * @return str
	 */
	public String parseMapToString(Map<String, String> params, boolean encode) {
		List<String> paramList = new ArrayList<>();
		forEach(params, (k, v) -> {
			if (v == null) {
				paramList.add(k + "=");
			} else {
				paramList.add(k + "=" + (encode ? UrlUtil.urlEncode(v) : v));
			}
		});
		return String.join("&", paramList);
	}
}

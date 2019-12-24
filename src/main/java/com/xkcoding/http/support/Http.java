package com.xkcoding.http.support;

import java.util.Map;

/**
 * <p>
 * HTTP 接口
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 18:21
 */
public interface Http {
    /**
     * GET 请求
     *
     * @param url URL
     * @return 结果
     */
    String get(String url);

    /**
     * GET 请求
     *
     * @param url    URL
     * @param params 参数
     * @return 结果
     */
    String get(String url, Map<String, String> params);

    /**
     * GET 请求
     *
     * @param url    URL
     * @param params 参数
     * @param header 请求头
     * @return 结果
     */
    String get(String url, Map<String, String> params, HttpHeader header);

    /**
     * POST 请求
     *
     * @param url  URL
     * @param data JSON 参数
     * @return 结果
     */
    String post(String url, String data);

    /**
     * POST 请求
     *
     * @param url    URL
     * @param data   JSON 参数
     * @param header 请求头
     * @return 结果
     */
    String post(String url, String data, HttpHeader header);

    /**
     * POST 请求
     *
     * @param url    URL
     * @param params form 参数
     * @return 结果
     */
    String post(String url, Map<String, String> params);

    /**
     * POST 请求
     *
     * @param url    URL
     * @param params form 参数
     * @param header 请求头
     * @return 结果
     */
    String post(String url, Map<String, String> params, HttpHeader header);
}

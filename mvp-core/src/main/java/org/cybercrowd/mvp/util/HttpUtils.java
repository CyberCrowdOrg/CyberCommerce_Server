package org.cybercrowd.mvp.util;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	// 超时时间60s
	private static final int MAX_TIMEOUT = 60000;
	private static ContentType contentType = ContentType.create(MIME.CONTENT_TYPE, Charsets.UTF_8);

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(5);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		requestConfig = configBuilder.build();
	}

	/**
	 * 填充请求头参数至get请求中
	 *
	 * @param httpPost
	 * @param headers
	 * @return
	 */
	private static HttpGet setHeadersToGet(HttpGet httpPost, Map<String, String> headers) {
		if (headers != null && headers.size() != 0) {
			for (String key : headers.keySet()) {
				httpPost.addHeader(key, headers.get(key));
			}
		}
		return httpPost;
	}

	/**
	 * 填充请求头参数至Post请求中
	 *
	 * @param httpPost
	 * @param headers
	 * @return
	 */
	private static HttpPost setHeadersToPost(HttpPost httpPost, Map<String, String> headers) {
		if (headers != null && headers.size() != 0) {
			for (String key : headers.keySet()) {
				httpPost.addHeader(key, headers.get(key));
			}
		}
		return httpPost;
	}

	/**
	 * 填充请求头参数至Put请求中
	 *
	 * @param httpPut
	 * @param headers
	 * @return
	 */
	private static HttpPut setHeadersToPut(HttpPut httpPut, Map<String, String> headers) {
		if (headers != null && headers.size() != 0) {
			for (String key : headers.keySet()) {
				httpPut.addHeader(key, headers.get(key));
			}
		}
		return httpPut;
	}

	/**
	 * 填充请求体参数至Post请求中
	 *
	 * @param httpPost
	 * @param params
	 * @return
	 */
	private static HttpPost setParamsToRequest(HttpPost httpPost, Map<String, Object> params) {

		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		// 设置编码
		multipartEntityBuilder.setCharset(Charsets.UTF_8);
		multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (entry.getValue() instanceof File) {
				// 文件参数
				multipartEntityBuilder.addBinaryBody(entry.getKey(), (File) entry.getValue());
			} else if (entry.getValue() instanceof String) {
				multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
			} else {
				multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue().toString(), contentType);
			}
		}
		httpPost.setEntity(multipartEntityBuilder.build());
		return httpPost;
	}

	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 *
	 * @param url
	 * @return
	 */
	public static RequestResult doGet(String url) {
		return doGet(url, new HashMap<String, Object>(), new HashMap<String, String>());
	}

	/**
	 * 发送 GET 请求（HTTP）,K-V形式,无请求头参数
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static RequestResult doGet(String url, Map<String, Object> params) {
		return doGet(url, params, null);
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式，有请求头参数
	 *
	 * @param url     API接口URL
	 * @param params  参数map
	 * @param headers 请求头参数
	 * @return
	 */
	public static RequestResult doGet(String url, Map<String, Object> params, Map<String, String> headers) {

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		HttpGet httpGet = null;
		HttpResponse response = null;
		RequestResult requestResult = new RequestResult();
		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(params.get(key));
				i++;
			}
		}
		apiUrl += param;
		String result = null;
		try {
			HttpEntity entity = null;
			httpGet = new HttpGet(apiUrl);
			httpGet = setHeadersToGet(httpGet, headers);
			response = httpClient.execute(httpGet);
			if (response != null) {
				entity = response.getEntity();
			}

			if (entity != null) {
				// InputStream instream = entity.getContent();
				// result = IOUtils.toString(instream, "UTF-8");
				result = EntityUtils.toString(entity, Charset.forName("UTF-8"));

				requestResult.setResult(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			requestResult.setStatusCode(response.getStatusLine().getStatusCode());
		}
		return requestResult;
	}

	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 *
	 * @param url
	 * @return
	 */
	public static String doPost(String url) {
		return doPost(url, new HashMap<String, Object>(), new HashMap<String, String>());
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式，无请求头参数
	 *
	 * @param url
	 * @param json json对象
	 * @return
	 */
	public static RequestResult doPost(String url, Object json) {
		return doPost(url, json, null);
	}

	/**
	 * 发送 POST 请求（HTTP）,K-V形式,无请求头参数
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> params) {
		return doPost(url, params, null);
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式 ，有请求头参数
	 *
	 * @param url     API接口URL
	 * @param params  参数map
	 * @param headers 请求头参数
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> params, Map<String, String> headers) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;

		httpPost.setConfig(requestConfig);

		if (!(null == headers)) {
			httpPost = setHeadersToPost(httpPost, headers);
		}

		httpPost = setParamsToRequest(httpPost, params);

		try {
			response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity entity = response.getEntity();
				httpStr = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式，有请求头参数
	 *
	 * @param url
	 * @param json    json对象
	 * @param headers 请求头参数
	 * @return
	 */
	public static RequestResult doPost(String url, Object json, Map<String, String> headers) {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		RequestResult requestResult = new RequestResult();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			httpPost = setHeadersToPost(httpPost, headers);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity entity = response.getEntity();
				httpStr = EntityUtils.toString(entity, "UTF-8");
			}
			requestResult.setResult(httpStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			requestResult.setStatusCode(response.getStatusLine().getStatusCode());
		}
		return requestResult;
	}
}

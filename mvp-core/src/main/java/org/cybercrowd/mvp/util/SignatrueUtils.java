package org.cybercrowd.mvp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 签名工具类 私钥签名，公钥加密
 * 
 * @author wangzhongbin
 *
 */
public class SignatrueUtils {
	private static final Logger logger = LoggerFactory.getLogger(SignatrueUtils.class);

	/**
	 * 字符串校验签名
	 * 
	 * @param text
	 * @param sign
	 * @param keyPath
	 * @return
	 */
	public static boolean vertifySignToString(String text, String sign, String keyPath) {
		logger.info("<<<<<<<<<<<<<<<<<<参与验签的字符串为:[" + text + "]");
		try {
			return RsaUtil.verify(text.getBytes("utf-8"), keyPath, sign);
		} catch (UnsupportedEncodingException e) {
			logger.info("<<<<<<<<<<<<<<<<<<校验签名异常", e);
			return false;
		} catch (Exception e) {
			logger.info("<<<<<<<<<<<<<<<<<<校验签名异常", e);
			return false;
		}
	}

	/**
	 * 校验签名
	 *
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean vertifySign(String text, String sign, String keyPath) {
		try {
			TreeMap<String, String> map = covertToTreeMap(text);
			String result = getValue(map);
			logger.info("<<<<<<<<<<<<<<<<<<参与验签的字符串为:[" + result + "]");
			boolean verify = RsaUtil.verify(result.getBytes("utf-8"), keyPath, sign);
			logger.info("验签结果：{},参与验签的字符串为:{}", verify, result);
			return verify;
		} catch (UnsupportedEncodingException e) {
			logger.info("<<<<<<<<<<<<<<<<<<校验签名异常", e);
			return false;
		} catch (Exception e) {
			logger.info("<<<<<<<<<<<<<<<<<<校验签名异常", e);
			return false;
		}
	}

//	/**
//	 * 获取签名
//	 * 
//	 * @param map
//	 * @return
//	 * @throws Exception
//	 */
//	public static String getSign(String text) {
//		try {
//			TreeMap<String, String> ss = covertToTreeMap(text);
//			String result = getValue(ss);
//			logger.info("参与签名的字符串为:[" + result + "]");
//			return RsaUtil.sign(result.getBytes("utf-8"), initKey(Constants.PRIVATE_KEY));
//		} catch (Exception e) {
//			logger.info("****签名异常", e);
//			return "";
//		}
//
//	}

	/**
	 * 获取签名
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getSign(String text, String privateKey) {
		try {
			TreeMap<String, String> ss = covertToTreeMap(text);
			String result = getValue(ss);
			logger.info("参与签名的字符串为:[" + result + "]");
			return RsaUtil.sign(result.getBytes("utf-8"), privateKey);
		} catch (Exception e) {
			logger.info("****签名异常", e);
			return "";
		}

	}

	/**
	 * convert String to TreeMap
	 * 
	 * @param text JSONMap
	 * @return TreeMap <String, String>
	 * @author wangzhongbin
	 * @since 1.0.0
	 */
	private static TreeMap<String, String> covertToTreeMap(String text) {
		TreeMap<String, String> textTree = new TreeMap<String, String>();
		Object object = JSON.parse(text);
		if (object instanceof JSONObject) {
			JSONObject jo = (JSONObject) object;
			Set<Entry<String, Object>> set = jo.entrySet();
			for (Entry<String, Object> str : set) {
				Object value = str.getValue();
				if (value != null && value instanceof String) {
					textTree.put(str.getKey(), value.toString());
				} else if (value instanceof JSONArray) {
					textTree.put(str.getKey(), JSON.toJSONString(formatJSONArray(JSON.toJSONString(value))));
				} else if (value instanceof JSONObject) {
					textTree.put(str.getKey(), JSON.toJSONString(covertToTreeMap(JSON.toJSONString(value))));
				} else {
					textTree.put(str.getKey(), value.toString());
				}
			}
		}

		return textTree;
	}

	/**
	 * convert each element to TreeMap
	 * 
	 * @param value JSONList
	 * @return List<TreeMap<String, String>>
	 * @author wangzhongbin
	 * @since 1.0.0
	 */
	private static List<TreeMap<String, String>> formatJSONArray(String value) {
		JSONArray ja = JSON.parseArray(value);
		List<TreeMap<String, String>> arrayList = Lists.newArrayList();
		for (int i = 0; i < ja.size(); i++) {
			arrayList.add(covertToTreeMap(JSON.toJSONString(ja.get(i))));
		}
		return arrayList;
	}

	/**
	 * 获取待签名的字符串
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String getValue(TreeMap<String, String> map) throws Exception {
		logger.info("签名mapcontent:[" + map.toString() + "]");
		map.remove("sign");// 移除上送上来的sign字段
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			sb.append(entry.getValue() == null ? "" : entry.getValue());
		}
		String result = sb.toString();
		logger.info("签名参数 ：" + result);
		return result;
	}

	/**
	 * 读取秘钥
	 */
	@SuppressWarnings("resource")
	private static String initKey(String keyFile) {
		try {
			String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			logger.info("秘钥路径" + path + keyFile);
			BufferedReader br = new BufferedReader(new FileReader(path + keyFile));
			String s = br.readLine();
			StringBuffer privatekey = new StringBuffer();
			s = br.readLine();
			while (s.charAt(0) != '-') {
				privatekey.append(s + "");
				s = br.readLine();
			}
			return privatekey.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	private static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成小写
			result.append(name.substring(0, 1).toLowerCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在小写字母前添加下划线
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toLowerCase());
			}
		}
		return result.toString();
	}

}

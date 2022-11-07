package org.cybercrowd.mvp.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 
 * @Title: ID工具类
 * @Description:
 * @author llj
 * @date 2020年1月11日 下午2:17:14
 */
@Component
public class IDUtils {

	private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

	private static IDUtils idUtils;

	@PostConstruct
	private void init() {
		idUtils = this;
	}

	/**
	 * Twitter的Snowflake 算法
	 * 
	 * @return
	 */
	public static String getIdBySnowflake() {
		return snowflake.nextIdStr();
	}

	public static String orderNoGenerate(String tag){
		return tag + snowflake.nextIdStr();
	}


	/**
	 * 用户ID
	 *
	 * @return
	 */
	public static Long getUserId() {
		String idBySnowflake = getIdBySnowflake();
		idBySnowflake = idBySnowflake.subSequence(0, 18).toString();
		return Long.valueOf(idBySnowflake);
	}

	public static String getTaskId(){
		String idBySnowflake = getIdBySnowflake();
		idBySnowflake = idBySnowflake.subSequence(0, idBySnowflake.length()-1).toString();
		return "T" + idBySnowflake;
	}

	public static String getProposalNo(){
		String idBySnowflake = getIdBySnowflake();
		idBySnowflake = idBySnowflake.subSequence(0, idBySnowflake.length()-2).toString();
		return "P" + idBySnowflake;
	}

	public static String getDaoNo(){
		String idBySnowflake = getIdBySnowflake();
		idBySnowflake = idBySnowflake.subSequence(0, idBySnowflake.length()-2).toString();
		return "D" + idBySnowflake;
	}

	public static String getOrderNo(){
		String idBySnowflake = getIdBySnowflake();
		idBySnowflake = idBySnowflake.subSequence(0, idBySnowflake.length()-1).toString();
		return "ODR" + idBySnowflake;
	}

	public static String getPayNo(){
		String idBySnowflake = getIdBySnowflake();
		idBySnowflake = idBySnowflake.subSequence(0, idBySnowflake.length()-1).toString();
		return "PAY" + idBySnowflake;
	}

}

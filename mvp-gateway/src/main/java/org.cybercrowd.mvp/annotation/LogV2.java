package org.cybercrowd.mvp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Title: 自定义操作日志记录注解 V2版本
 * @Description:
 * @author llj
 * @date 2019年11月4日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogV2 {
	/**
	 * 日志内容
	 * 
	 * @return
	 */
	String value() default "";

}

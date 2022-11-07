package org.cybercrowd.mvp.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 商户审核状态检查
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MerchantCheckStatus {

    /**
     * @return
     */
    String value() default "";
}

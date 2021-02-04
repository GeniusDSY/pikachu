package cn.edu.cqupt.pikachu.ad.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author :DengSiYuan
 * @date :2021/1/28 21:04
 * @desc : 忽略响应处理注解
 */
@Target({ElementType.TYPE,
        ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}

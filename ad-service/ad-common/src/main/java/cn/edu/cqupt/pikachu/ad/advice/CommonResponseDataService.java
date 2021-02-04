package cn.edu.cqupt.pikachu.ad.advice;

import cn.edu.cqupt.pikachu.ad.annotation.IgnoreResponseAdvice;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author :DengSiYuan
 * @date :2021/1/28 20:57
 * @desc : 统一响应数据服务
 */
@Slf4j
@RestControllerAdvice
public class CommonResponseDataService implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 判断类上是否有忽略响应数据处理的注解
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }

        // 判断方法上是否有忽略响应数据处理的注解
        if (null == methodParameter.getMethodAnnotation(IgnoreResponseAdvice.class)) {
            return false;
        }
        return true;
    }


    @Nullable
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        try {
            if (null == o) {
                return ResponseUtils.success(ResultStatus.SUCCESS);
            }
            if (o instanceof Response) {
                return o;
            }
            return ResponseUtils.success(ResultStatus.SUCCESS, o);
        } catch (Exception e) {
            log.error("Response error：", e);
            return ResponseUtils.fail(ResultStatus.SYSTEM_ERROR);
        }

    }
}

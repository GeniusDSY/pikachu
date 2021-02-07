package cn.edu.cqupt.pikachu.ad.advice;

import cn.edu.cqupt.pikachu.ad.constants.enums.ResultStatus;
import cn.edu.cqupt.pikachu.ad.exception.AdException;
import cn.edu.cqupt.pikachu.ad.model.vo.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :DengSiYuan
 * @date :2021/1/30 11:41
 * @desc : 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 广告系统异常拦截处理方法
     *
     * @param request   Servlet请求
     * @param exception 广告系统异常
     * @return 异常处理响应结果
     */
    @ExceptionHandler({AdException.class})
    public Response<Object> handlerAdException(HttpServletRequest request, AdException exception) {
        log.error("Request：method = ", request.getMethod(), ". Exception = ", exception);
        return new Response<>(ResultStatus.SYSTEM_ERROR, exception.getMessage());
    }

}

package cn.edu.cqupt.pikachu.ad.filter;

import cn.edu.cqupt.pikachu.ad.common.GatewayConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :DengSiYuan
 * @date :2021/1/9 22:26
 * @desc : 请求后置拦截器
 */
@Slf4j
@Component
public class PostRequestFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        Long startTime = Long.parseLong(String.valueOf(requestContext.get(GatewayConstants.REQUEST_START_TIME)));
        String uri = request.getRequestURI();
        long duration = System.currentTimeMillis() - startTime;

        log.info("uri: " + uri + ", duration" + duration / 100 + "ms");

        return null;
    }
}

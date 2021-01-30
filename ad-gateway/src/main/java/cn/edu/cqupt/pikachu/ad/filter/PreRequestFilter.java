package cn.edu.cqupt.pikachu.ad.filter;

import cn.edu.cqupt.pikachu.ad.common.GatewayConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author :DengSiYuan
 * @date :2021/1/7 21:40
 * @desc : 前置请求过滤器
 */
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.set(GatewayConstants.REQUEST_START_TIME, System.currentTimeMillis());
        return null;
    }

}

package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class ZuulLoggingFilter extends ZuulFilter {

//    Logger log = LoggerFactory.getLogger(ZuulLoggingFilter.class);

    // 사전 필터 or 사후 필터 정의
    @Override
    public String filterType() {
        return "pre";
    }

    //필터 순서 정의
    @Override
    public int filterOrder() {
        return 1;
    }

    // 필터로 사용 O => true, 필터로 사용 X => false
    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 필터로서 어떤 동작하는지 정의
    @Override
    public Object run() throws ZuulException {
        log.info("********* printing logs: ");

        // 요청과 응답의 최상위 객체 => RequestContext
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("********* " + request.getRequestURI());
        return null;
    }
}

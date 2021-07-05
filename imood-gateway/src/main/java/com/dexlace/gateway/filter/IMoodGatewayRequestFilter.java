package com.dexlace.gateway.filter;

import com.dexlace.common.constant.IMoodConstant;
import com.dexlace.common.utils.IMoodUtil;
import com.dexlace.common.vo.IMoodResponse;
import com.dexlace.gateway.properties.IMoodGatewayProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: xiaogongbing
 * @Description: PreDecorationFilter用于处理请求上下文，优先级为5，
 * 所以我们可以定义一个优先级在PreDecorationFilter之后的过滤器，这样便可以拿到请求上下文。
 * @Date: 2021/7/1
 */
@Slf4j
@Component
public class IMoodGatewayRequestFilter  extends ZuulFilter {

    @Autowired
    private IMoodGatewayProperties properties;


    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
        HttpServletRequest request = ctx.getRequest();
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info("请求URI：{}，HTTP Method：{}，请求IP：{}，ServerId：{}", uri, method, host, serviceId);


        // 禁止外部访问资源实现
        boolean shouldForward = true;
        // 获取外部资源访问的url
        String forbidRequestUri = properties.getForbidRequestUri();
        // 获取列表
        String[] forbidRequestUris = StringUtils.splitByWholeSeparatorPreserveAllTokens(forbidRequestUri, ",");
        if (forbidRequestUris != null && ArrayUtils.isNotEmpty(forbidRequestUris)) {
            for (String u : forbidRequestUris) {
                if (pathMatcher.match(u, uri)) {
                    shouldForward = false;
                }
            }
        }

        // 不允许外部访问
        if (!shouldForward) {
            HttpServletResponse response = ctx.getResponse();
            IMoodResponse febsResponse = new IMoodResponse().message("该URI不允许外部访问");
            try {

                IMoodUtil.makeResponse(
                        response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                        HttpServletResponse.SC_FORBIDDEN, febsResponse
                );
                ctx.setSendZuulResponse(false);
                ctx.setResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }







        byte[] token = Base64Utils.encode(IMoodConstant.ZUUL_TOKEN_VALUE.getBytes());
        ctx.addZuulRequestHeader(IMoodConstant.ZUUL_TOKEN_HEADER, new String(token));
        return null;
    }
}

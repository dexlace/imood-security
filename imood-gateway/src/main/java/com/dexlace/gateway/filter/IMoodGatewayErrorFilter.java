package com.dexlace.gateway.filter;

import com.dexlace.common.utils.IMoodUtil;
import com.dexlace.common.vo.IMoodResponse;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
@Slf4j
@Component
public class IMoodGatewayErrorFilter  extends SendErrorFilter {
    @Override
    public Object run() {
        try {
            IMoodResponse imoodResponse = new IMoodResponse();
            // 得到上下文请求对象
            RequestContext ctx = RequestContext.getCurrentContext();
            String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);

            ExceptionHolder exception = findZuulException(ctx.getThrowable());
            String errorCause = exception.getErrorCause();
            Throwable throwable = exception.getThrowable();
            String message = throwable.getMessage();
            message = StringUtils.isBlank(message) ? errorCause : message;
            imoodResponse = resolveExceptionMessage(message, serviceId, imoodResponse);

            HttpServletResponse response = ctx.getResponse();
            IMoodUtil.makeResponse(
                    response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, imoodResponse
            );
            log.error("Zull sendError：{}", imoodResponse.getMessage());
        } catch (Exception ex) {
            log.error("Zuul sendError", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

    private IMoodResponse resolveExceptionMessage(String message, String serviceId, IMoodResponse imoodResponse) {
        if (StringUtils.containsIgnoreCase(message, "time out")) {
            return imoodResponse.message("请求" + serviceId + "服务超时");
        }
        if (StringUtils.containsIgnoreCase(message, "forwarding error")) {
            return imoodResponse.message(serviceId + "服务不可用");
        }
        return imoodResponse.message("Zuul请求" + serviceId + "服务异常");
    }
}

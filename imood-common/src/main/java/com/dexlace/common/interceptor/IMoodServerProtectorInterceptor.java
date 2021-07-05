package com.dexlace.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.dexlace.common.constant.IMoodConstant;
import com.dexlace.common.vo.IMoodResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
public class IMoodServerProtectorInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 从请求头中获取 Zuul Token
        String token = request.getHeader(IMoodConstant.ZUUL_TOKEN_HEADER);
        String zuulToken = new String(Base64Utils.encode(IMoodConstant.ZUUL_TOKEN_VALUE.getBytes()));
        // 校验 Zuul Token的正确性
        if (StringUtils.equals(zuulToken, token)) {
            return true;
        } else {
            IMoodResponse imoodResponse = new IMoodResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(JSONObject.toJSONString(imoodResponse.message("请通过网关获取资源")));
            return false;
        }
    }

}

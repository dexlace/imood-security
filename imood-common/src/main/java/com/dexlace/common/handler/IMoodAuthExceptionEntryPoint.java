package com.dexlace.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.dexlace.common.utils.IMoodUtil;
import com.dexlace.common.vo.IMoodResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: xiaogongbing
 * @Description: 资源服务器异常处理 令牌不正确返回401
 * @Date: 2021/7/1
 */
public class IMoodAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        IMoodResponse imoodResponse = new IMoodResponse();

//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(401);
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getOutputStream().write(JSONObject.toJSONString(imoodResponse.message("token无效")).getBytes());
        IMoodUtil.makeResponse(response,MediaType.APPLICATION_JSON_UTF8_VALUE,
                HttpServletResponse.SC_UNAUTHORIZED,imoodResponse.message("token无效"));
    }
}

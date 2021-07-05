package com.dexlace.auth.controller;

import com.dexlace.auth.service.ValidateCodeService;
import com.dexlace.common.exception.IMoodAuthException;
import com.dexlace.common.exception.ValidateCodeException;
import com.dexlace.common.vo.IMoodResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/30
 */
@RestController
public class SecurityController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private ValidateCodeService validateCodeService;

    @GetMapping("oauth/test")
    public String testOauth() {
        return "oauth";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @DeleteMapping("signout")
    public IMoodResponse signout(HttpServletRequest request) throws IMoodAuthException {
        String authorization = request.getHeader("Authorization");
        String token = StringUtils.replace(authorization, "bearer ", "");
        IMoodResponse imoodResponse = new IMoodResponse();
        if (!consumerTokenServices.revokeToken(token)) {
            throw new IMoodAuthException("退出登录失败");
        }
        return imoodResponse.message("退出登录成功");
    }


    /**
     * 因为我们验证码是供客户端认证的时候使用的，这时候客户端还没有获取到令牌，所以我们的验证码生成服务需要配置为免认证
     * @param request
     * @param response
     * @throws IOException
     * @throws ValidateCodeException
     */
    @GetMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException, ValidateCodeException {
        validateCodeService.create(request, response);
    }
}

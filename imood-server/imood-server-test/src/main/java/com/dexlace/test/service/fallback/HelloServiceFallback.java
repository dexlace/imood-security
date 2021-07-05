package com.dexlace.test.service.fallback;

import com.dexlace.test.service.IHelloService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
@Slf4j
@Component
public class HelloServiceFallback implements FallbackFactory<IHelloService> {
    @Override
    public IHelloService create(Throwable throwable) {
        return new IHelloService() {
            @Override
            public String hello(String name) {
                log.error("调用imood-server-system服务出错", throwable);
                return "调用出错";
            }
        };
    }
}

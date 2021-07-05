package com.dexlace.test.service;

import com.dexlace.test.service.fallback.HelloServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: xiaogongbing
 * @Description: contextId指定这个Feign Client的别名
 * @Date: 2021/7/1
 */
@FeignClient(value = "IMOOD-Server-System", contextId = "helloServiceClient", fallbackFactory = HelloServiceFallback.class)
public interface IHelloService {

    @GetMapping("hello")
    String hello(@RequestParam("name") String name);
}
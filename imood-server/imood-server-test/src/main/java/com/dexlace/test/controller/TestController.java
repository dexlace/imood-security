package com.dexlace.test.controller;

import com.dexlace.test.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Author: xiaogongbing
 * @Description: 因为Feign在调用远程服务的时候，并不会帮我们把原HTTP请求头部的内容也携带上，
 * 所以访问imood-server-system的/hello服务的时候，请求头部没有访问令牌，于是抛出了401异常。
 * @Date: 2021/6/30
 */
@RestController
public class TestController {
    @Autowired
    private IHelloService helloService;

    @GetMapping("hello")
    public String hello(String name){
        System.out.println("heeeeee");
        return this.helloService.hello(name);
    }

    @GetMapping("test1")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public String test1(){
        return "拥有'user:add'权限";
    }

    @GetMapping("test2")
    @PreAuthorize("hasAnyAuthority('user:update')")
    public String test2(){
        return "拥有'user:update'权限";
    }

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }
}
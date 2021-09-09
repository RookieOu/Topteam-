package com.topteam.lol.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对外提供服务的接口使用这个注解
 * @author qiaoyuan
 * @date 2021/5/7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RestController
@RequestMapping
@Encrypted
public @interface TptController {

    @AliasFor(annotation = RequestMapping.class)
    String value();
}

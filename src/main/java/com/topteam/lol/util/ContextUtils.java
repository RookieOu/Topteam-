package com.topteam.lol.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * SpringContext工具类
 */
@SuppressWarnings("unchecked")
public final class ContextUtils {

    private static ApplicationContext applicationContext;

    private ContextUtils() {
    }

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象
     *
     * @param clazz
     * @return Object 一个以类型注册的bean实例
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException {
        checkApplicationContext();
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取对象
     * @param clazz
     * @param param 属性参数
     * @param <T>
     * @return Object 一个以类型注册的bean实例
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clazz, Object... param) throws BeansException {
        checkApplicationContext();
        return applicationContext.getBean(clazz, param);
    }

    /**
     * 获取对象
     *
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public static <T> T getBean(String name) throws BeansException {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获取对象
     *
     * @param name
     * @param param 属性参数
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public static <T> T getBean(String name, Object... param) throws BeansException {
        checkApplicationContext();
        return (T) applicationContext.getBean(name, param);
    }

    /**
     * 获取特定类型的所有对象
     *
     * @param clazz
     * @param <T>
     * @return Map<String, T>
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 获取拥有特定注解的所有Bean
     *
     * @param annotationType
     * @return Map<String, Object>
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        checkApplicationContext();
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    /**
     * 创建原型Bean
     *
     * @param clazz
     * @param param 属性参数
     * @param <T>
     * @return T
     */
    public static <T> T createPrototypeBean(Class<T> clazz, Object... param) {
        return getBean(clazz, param);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        checkApplicationContext();
        return applicationContext.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name
     * @return boolean
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        checkApplicationContext();
        return applicationContext.isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        checkApplicationContext();
        return applicationContext.getType(name);
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            String info = "application context not initialized";
            throw new ApplicationContextException(info);
        }
    }

}


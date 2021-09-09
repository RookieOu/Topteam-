package com.topteam.lol.util;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ReflectUtils {

    private ReflectUtils() {
    }

    /**
     * 获取注解实例，包含父类注解
     * @param clazz
     * @param annoClazz
     * @return anno
     */
    public static <A extends Annotation> A getAnnoWhatEver(Class<?> clazz, Class<? extends A> annoClazz) {
        Class<?> c = clazz;
        do {
            A anno = c.getAnnotation(annoClazz);
            if (anno != null) {
                return anno;
            }
            c = c.getSuperclass();
        } while (c != null);
        return null;
    }

    /**
     * 获取特定包下特定类型的所有子类
     *
     * @param packageName
     * @param superClass
     * @param <T>
     * @return Set<Class<?>> classSet
     */
    public static <T> Set<Class<? extends T>> getSubTypesOf(String packageName, Class<T> superClass) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends T>> classSet = reflections.getSubTypesOf(superClass);
        return classSet;
    }

    /**
     * 获取特定包下拥有特定注解的所有类
     *
     * @param packageName
     * @param annoClass
     * @return Set<Class<?>> classSet
     */
    public static Set<Class<?>> getTypesAnnotatedWith(String packageName, Class<? extends Annotation> annoClass) {
        var reflections = new Reflections(packageName);
        var classSet = reflections.getTypesAnnotatedWith(annoClass);
        return classSet;
    }

    /**
     * 获取特定类中拥有特定注解的所有方法
     *
     * @param clz
     * @param annoClass
     * @return List<Method> 方法列表
     */
    public static List<Method> getMethodsAnnotatedWith(Class<?> clz, Class<? extends Annotation> annoClass) {
        var list = new ArrayList<Method>();
        for (var m : clz.getMethods()) {
            if (m.isAnnotationPresent(annoClass)) {
                list.add(m);
            }
        }
        return list;
    }
}


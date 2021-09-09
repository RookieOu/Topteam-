package com.topteam.lol.handler;

import com.google.common.collect.Maps;
import com.topteam.lol.annotation.CmdMethod;
import com.topteam.lol.model.CommandFunction;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * @author yanou
 */
public class CommandHandler {

    private Object bean;
    private Map<String, CommandFunction> functions = Maps.newHashMap();
    private String name;
    private String desc;

    public CommandHandler(String name,String desc, Object bean, List<Method> methods) {
        this.name = name;
        this.bean = bean;
        this.desc = desc;
        initFunctions(methods);
    }

    private void initFunctions(List<Method> methods) {
        for (var method : methods) {
            var annotation = method.getAnnotation(CmdMethod.class);
            var funcName = annotation.value().isBlank() ? method.getName() : annotation.value();
            var function = new CommandFunction(funcName, this, method);
            functions.put(funcName, function);
        }
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Object getBean() {
        return bean;
    }

    /**
     * 执行函数
     *
     * @param funcName
     * @param args
     */
    public Object execFunc(String funcName, String args) throws Exception {
        var function = functions.get(funcName);
        if (function == null) {
            throw new Exception("ds！");
        }
        return function.exec(args);
    }
}


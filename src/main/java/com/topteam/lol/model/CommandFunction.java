package com.topteam.lol.model;

import com.topteam.lol.handler.CommandHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yanou
 */
public class CommandFunction {

    private String name;
    private CommandHandler handler;
    private Method method;

    public CommandFunction(String name, CommandHandler handler, Method method) {
        this.name = name;
        this.handler = handler;
        this.method = method;
    }

    /**
     * 执行
     *
     * @param args
     */
    public Object exec(String args) throws Exception {
        try {
            return method.invoke(handler.getBean(), args);
        } catch (Exception e) {
            Throwable t;
            if (e instanceof InvocationTargetException) {
                t = ((InvocationTargetException) e).getTargetException();
            } else {
                t = e;
            }
            throw e;
        }
    }
}

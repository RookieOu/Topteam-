package com.topteam.lol.service;

import com.google.common.collect.Maps;
import com.topteam.lol.annotation.CmdHandler;
import com.topteam.lol.annotation.CmdMethod;
import com.topteam.lol.command.Command;
import com.topteam.lol.handler.CommandHandler;
import com.topteam.lol.util.ContextUtils;
import com.topteam.lol.util.ReflectUtils;
import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanou
 */
public class CommandService {


    public static final String helpCommand = "help";
    public static final String HANDLER_SUFFIX = "Handler";
    private static final String HANDLERS_PACKAGE = "com.topjoy.japari.gm.command.handlers";
    private Map<String, CommandHandler> handlers = Maps.newHashMap();

    /**
     * 初始化
     */
    public CommandService() {
        init();
    }

    public Map<String, String> getHandlersDesc() {
        Map<String, String> desc = new HashMap<>(8);
        handlers.values().forEach(x -> {
            desc.put(x.getName() + " " + helpCommand, x.getDesc());
        });
        return desc;
    }

    /**
     * 执行command命令
     */
    public Response execCommand(Command command) throws Exception {

        CommandHandler handler = handlers.get(command.getHandler());
        if (handler == null) {
            throw new Exception("nnal");
        }
        Response response = (Response) handler.execFunc(command.getMethod(), command.getParameter());
        return response;
    }

    private void init() {
        var handlerClzSet = ReflectUtils.getTypesAnnotatedWith(HANDLERS_PACKAGE, CmdHandler.class);
        for (var handlerClz : handlerClzSet) {
            CmdHandler annotation = handlerClz.getAnnotation(CmdHandler.class);
            String commandName = annotation.value().isBlank() ?
                    handlerClz.getSimpleName().replace(HANDLER_SUFFIX, "") : annotation.value();
            commandName = commandName.substring(0, 1).toLowerCase() + commandName.substring(1);
            String desc = annotation.desc();
            if (handlers.containsKey(commandName)) {
                continue;
            }
            try {
                var methods = ReflectUtils.getMethodsAnnotatedWith(handlerClz, CmdMethod.class);
                Object bean = ContextUtils.createPrototypeBean(handlerClz);
                var handler = new CommandHandler(commandName, desc, bean, methods);
                handlers.put(commandName, handler);
            } catch (Exception e) {
            }
        }

    }
}

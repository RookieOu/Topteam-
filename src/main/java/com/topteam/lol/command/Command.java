package com.topteam.lol.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qiaoyuan
 */
public class Command {

    private final static Pattern PATTERN = Pattern.compile("(\\w+)(\\s+)(\\w+)(\\s*)(\\S*.*$)");

    private String handler;

    private String method;

    private String parameter;

    public String getHandler() {
        return handler;
    }

    public String getMethod() {
        return method;
    }

    public String getParameter() {
        return parameter;
    }

    public Command(String handler, String method, String parameter) {
        this.handler = handler;
        this.method = method;
        this.parameter = parameter;
    }

    public static Command parseCmd(String command) {
        Matcher m = PATTERN.matcher(command.trim());
        m.find();
        String handle = m.group(1);
        String method = m.group(3);
        String parameter = m.group(5);

        return new Command(
                handle,
                method,
                parameter
        );
    }
}


package com.topteam.lol.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "dingtalk")
public class DingTalkRobotConfig {
    private boolean robotEnable;
    private String robotToken;
    private String robotSecret;

    public boolean isRobotEnable() {
        return robotEnable;
    }

    public void setRobotEnable(boolean robotEnable) {
        this.robotEnable = robotEnable;
    }

    public String getRobotToken() {
        return robotToken;
    }

    public void setRobotToken(String robotToken) {
        this.robotToken = robotToken;
    }

    public String getRobotSecret() {
        return robotSecret;
    }

    public void setRobotSecret(String robotSecret) {
        this.robotSecret = robotSecret;
    }
}

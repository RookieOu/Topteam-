package com.topteam.lol.service;

import com.topteam.lol.robot.DingTalkRobot;
import org.springframework.stereotype.Service;

@Service
public class SendService {
    private static final String SHANG_HAO = "sdsh";

    private static final String ZUI_CHOU = "nnal";

    private final DingTalkRobot dingTalkRobot;

    public SendService(DingTalkRobot dingTalkRobot) {
        this.dingTalkRobot = dingTalkRobot;
    }

    public void callAllPlayer() {
        dingTalkRobot.sendText(SHANG_HAO);
    }

    public void callOne(String name) {
        dingTalkRobot.sendText("@" + name + " " + SHANG_HAO);
    }

    public void sendText(String text) {
        dingTalkRobot.sendText(text);
    }
}

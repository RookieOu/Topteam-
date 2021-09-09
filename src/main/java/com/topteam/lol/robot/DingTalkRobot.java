package com.topteam.lol.robot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.topteam.lol.config.DingTalkRobotConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Component
public class DingTalkRobot {
    private final static String HOST = "https://oapi.dingtalk.com";
    private final static String MESSAGE_PATH = "/robot/send";
    private final DingTalkRobotConfig dingTalkRobotConfig;

    public DingTalkRobot(DingTalkRobotConfig dingTalkRobotConfig) {
        this.dingTalkRobotConfig = dingTalkRobotConfig;
    }

    private String getToken(){
        return dingTalkRobotConfig.getRobotToken();
    }

    private String getSecret(){
        return dingTalkRobotConfig.getRobotSecret();
    }

    public void sendText(String text) {
        TextContent textContent = new TextContent(text);
        sendContent(textContent);
    }

    private<ToJSON> void sendContent(ToJSON content) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        HttpEntity<ToJSON> data = new HttpEntity<>(content, headers);
        long timestamp = System.currentTimeMillis();
        Optional<String> signature = sign(timestamp, getSecret());
        signature.ifPresent(s -> {
            String url = HOST + MESSAGE_PATH + "?access_token=" + getToken() + "&timestamp=" + timestamp + "&sign=" + s;
            var response = template.postForObject(url, data, String.class);
        });
    }

    private static Optional<String> sign(long timestamp, String secret) {
        if (secret == null || secret.isEmpty()) {
            return Optional.empty();
        }
        String stringToSign = timestamp + "\n" + secret;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            String result = URLEncoder.encode(Base64.getEncoder().encodeToString(signData), StandardCharsets.UTF_8);
            return Optional.of(result);
        } catch (NoSuchAlgorithmException | InvalidKeyException ignored) {
        }
        return Optional.empty();
    }

    private static class TextContent {
        private final Map<String, String> text;

        public TextContent(String message) {
            this.text = Map.of("content", message);
        }

        @JsonProperty("msgtype")
        public String getMsgType() {
            return "text";
        }

        @JsonProperty("text")
        public Map<String, String> getText() {
            return text;
        }
    }
}



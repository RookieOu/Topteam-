package com.topteam.lol;

import com.topteam.lol.annotation.TptController;
import com.topteam.lol.service.SendService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author yanou
 */
@TptController(value = "/topteam")
public class LolController {

    private final SendService sendService;

    public LolController(SendService sendService) {
        this.sendService = sendService;
    }


    @PostMapping("/all")
    String all(@RequestParam Map<String, String> params) {
        sendService.callAllPlayer();
        return "ok";
    }

    @PostMapping("/one")
    String one(@RequestParam Map<String, String> params) {
        String name = params.get("name");
        sendService.callOne(name);
        return "ok";
    }
}

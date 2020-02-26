package com.greengiant.website.controller.websocket;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/ws")
public class WebSocketTestController {

    @RequestMapping(value = "/send", method = {RequestMethod.GET})
    public ResultBean send(@RequestParam String message, @RequestParam String type) {
        try {
            WebSocketServer.sendInfo(message, type);
        } catch (IOException ex) {
            // todo
        }

        return ResultUtils.success("success");
    }

}

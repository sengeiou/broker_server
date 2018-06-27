package com.xyauto.interact.broker.server.controller;

import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.util.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Autowired
    Result result;

    @RequestMapping(value = "/info")
    public Result info() {
        return result.format(ResultCode.Success);
    }

}

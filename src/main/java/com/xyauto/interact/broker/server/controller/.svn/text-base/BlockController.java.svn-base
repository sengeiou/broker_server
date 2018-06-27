package com.xyauto.interact.broker.server.controller;

import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.Blocks;
import com.xyauto.interact.broker.server.service.BlocksService;
import com.xyauto.interact.broker.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/block")
public class BlockController extends BaseController {

    @Autowired
    private BlocksService blocksService;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "app_id", required = true) String app_id
    ) {
        Blocks block = null;
        try {
            block = blocksService.get(name);
        } catch (ResultException e) {
            return result.format(e.getResult());
        }
        return result.format(ResultCode.Success, block);
    }

}

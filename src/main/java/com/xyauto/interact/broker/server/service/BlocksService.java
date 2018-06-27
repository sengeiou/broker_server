package com.xyauto.interact.broker.server.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xyauto.interact.broker.server.dao.proxy.BlocksDaoProxy;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.Blocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlocksService {

    @Autowired
    private BlocksDaoProxy blocksDao;


    public Blocks get(String name) throws ResultException {
        Blocks block = blocksDao.get(name);
        if (block == null) {
            throw new ResultException(ResultCode.BlockNotFound);
        }
        if (((String) block.getContent()).startsWith("{")) {
            JSONObject json = JSON.parseObject(block.getContent().toString());
            if (json != null) {
                block.setContent(json);
            }
        } else if (((String) block.getContent()).startsWith("[")) {
            JSONArray jsonArr = JSON.parseArray(block.getContent().toString());
            if (jsonArr != null) {
                block.setContent(jsonArr);
            }
        }
        return block;
    }
    
    public String getBlockByName(String name) throws ResultException{
    	Blocks block = blocksDao.get(name);  
    	if (block==null) {
    		throw new ResultException(ResultCode.BlockNotFound);
		}
        return block.getContent().toString();
    }

}

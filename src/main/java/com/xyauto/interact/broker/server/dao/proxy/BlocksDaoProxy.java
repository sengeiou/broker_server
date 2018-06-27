package com.xyauto.interact.broker.server.dao.proxy;

import com.xyauto.interact.broker.server.dao.IBlocksDao;
import com.xyauto.interact.broker.server.model.vo.Blocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shiqm on 2018-03-13.
 */

@Component
public class BlocksDaoProxy {

    @Autowired
    private IBlocksDao iBlocksDao;

    public Blocks get(String name) {
        return iBlocksDao.get(name);
    }


}

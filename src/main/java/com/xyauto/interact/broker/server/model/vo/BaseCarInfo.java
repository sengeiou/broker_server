package com.xyauto.interact.broker.server.model.vo;

import org.apache.commons.lang.StringUtils;

public class BaseCarInfo {
    /**
     * 显示名称
     */
    private  String showname = StringUtils.EMPTY;
    /**
     * 最高指导价
     */
    private  float  dealermaxprice;
    /**
     * 最低指导价
     */
    private  float  dealerminprice;
    /**
     * 车型id
     */
    private  int id;
    /**
     * pv
     */
    private  int pv;
    /**
     * uv
     */
    private  int uv;

    /**
     *
     * @return
     */

    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
    }

    public float getDealermaxprice() {
        return dealermaxprice;
    }

    public void setDealermaxprice(float dealermaxprice) {
        this.dealermaxprice = dealermaxprice;
    }

    public float getDealerminprice() {
        return dealerminprice;
    }

    public void setDealerminprice(float dealerminprice) {
        this.dealerminprice = dealerminprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }
}

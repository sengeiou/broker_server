package com.xyauto.interact.broker.server.model.vo;

public class DealerCar {

    public long getDealerCarId() {
        return dealerCarId;
    }

    public void setDealerCarId(long dealerCarId) {
        this.dealerCarId = dealerCarId;
    }

    public long getDealerId() {
        return dealerId;
    }

    public void setDealerId(long dealerId) {
        this.dealerId = dealerId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getSubBrandId() {
        return subBrandId;
    }

    public void setSubBrandId(int subBrandId) {
        this.subBrandId = subBrandId;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    /**
     * 经纪人在售车款id
     */
    private long dealerCarId;

    /**
     * 经销商id
     */
    private long dealerId;

    /**
     * 品牌id
     */
    private int brandId;

    /**
     * 子品牌id
     */
    private int subBrandId;

    /**
     * 车系id
     */
    private int seriesId;

    /**
     * 车款id
     */
    private int carId;
}

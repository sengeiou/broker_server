package com.xyauto.interact.broker.server.model.vo;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class BrokerReceiptExt {
    public int year;
    public int month;
    public List<BrokerReceipt> brokerReceipts;

    public BrokerReceiptExt(){

    }

    public BrokerReceiptExt(int year,int month,BrokerReceipt t){
        this.year =year;
        this.month =month;
        this.brokerReceipts = new ArrayList<BrokerReceipt>();
        this.brokerReceipts.add(t);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<BrokerReceipt> getBrokerReceipts() {
        return brokerReceipts;
    }

    public void setBrokerReceipts(List<BrokerReceipt> brokerReceipts) {
        this.brokerReceipts = brokerReceipts;
    }

}

package com.xyauto.interact.broker.server.enums;

import java.net.URLEncoder;

public enum PushMessage {
    
    NewClue("1条新线索出炉了，快去看看吧，点击直接查看线索", "huisj://tab1"),
    WeekReport("上周业绩新鲜出炉，快去看看吧，点击查看详情", "huisj://webView?url="+URLEncoder.encode("http://hsjh5.qichedaquan.com/reports/month&title="+URLEncoder.encode("上周业绩"))),
    CustomerNotice("您设置的客户%s的跟进时间就要到了，点击直接联系您的客户吧", "huisj://clientDetail?customerID=%s"),
    Task12ProgressNotice("您还有很多任务么有完成呢，加油哦，点击完成任务", "huisj://integral"),
    Task17ProgressNotice("今天的任务还差一点就要完成了呦，点击去完成任务", "huisj://integral");
    
    
    private String value = "";
    private String link = "";
    
    PushMessage(String value, String link){
        this.value = value;
        this.link = link;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }
}

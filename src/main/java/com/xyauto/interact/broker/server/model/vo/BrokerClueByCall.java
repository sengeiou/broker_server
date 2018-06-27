package com.xyauto.interact.broker.server.model.vo;

import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class BrokerClueByCall {

    /**
     * @return the calleeCustomer
     */
    public BrokerCustomer getCalleeCustomer() {
        return calleeCustomer;
    }

    /**
     * @param calleeCustomer the calleeCustomer to set
     */
    public void setCalleeCustomer(BrokerCustomer calleeCustomer) {
        this.calleeCustomer = calleeCustomer;
    }
    private int billingduration;
    /**
     * 通话回答时间
     */
    private long callanswertime;
    /**
     * 呼叫开始时间
     */
    private long callbegintime;
    /**
     * 通话秒数
     */
    private int calleeduration;


    /**
     * 等待时长  通过  callbegintime - callanswertime 计算出   00:00:15
     */
    private String callwaittimedesc = StringUtils.EMPTY;

    /**
     * 根据通话秒数计算出 00:00:15 数据
     */
    private String calleedurationdescription = StringUtils.EMPTY;

    /**
     * 挂机时间
     */
    private long hangUpTime;

    /**
     * 分机按键
     */
    private String calleeextennumber = StringUtils.EMPTY;
    /**
     * 被叫号码
     */
    private String calleenumber = StringUtils.EMPTY;
    /**
     * 被叫95号码
     */
    private String calleephonenumber = StringUtils.EMPTY;
    /**
     * 被叫真实号码
     */
    private String calleerealnumber = StringUtils.EMPTY;
    /**
     * 呼叫秒数
     */
    private int callerduration;
    private int callerlocationid;

    /**
     * 呼叫人号码归属地
     */
    private String callerlocationname = StringUtils.EMPTY;
    /**
     * 主叫号码
     */
    private String callerphonenumber = StringUtils.EMPTY;
    private Date callextentime;
    /**
     * 话单表主键id
     */
    private long calllogid;
    /**
     * 呼叫状态  1
     */
    private String callstatusname;
    /**
     * 车款ID
     */
    private int carid;

    /**
     * 经销商ID
     */
    private int dealerid;
    /**
     * 来源id
     */
    private long id;
    /**
     * 物料ID
     */
    private int materialid;
    /**
     * 新闻ID
     */
    private int newsid;
    private int recordingfileisshow;
    private int recordingfiletype;
    /**
     * 通话录音地址
     */
    private String recordingfileurl = StringUtils.EMPTY;
    /**
     * 车型ID
     */
    private int serialid;
    /**
     * 来源 1、汽车大全APP-IOS，2、M站，3、微店，4、汽车大全APP-android，5、汽车大全H5，6小程序
     */
    private int sourceid;
    /**
     * 响应日志
     */
    private String tracefileurl = StringUtils.EMPTY;
    /**
     * 用户cookie
     */
    private String usercookie = StringUtils.EMPTY;

    /**
     * 通话所带参数
     */
    private String userfield = StringUtils.EMPTY;

    /**
     * 经纪人或经销商ID
     */
    private int userid;
    /**
     * 服务表主键id
     */
    private int userserviceid;
    private int userservicestatus;

    /**
     * 客户类型 1：经纪人 4经销商
     */
    private int usertypeid;


    private Broker callerBroker;

    private BrokerCustomer calleeCustomer;

    public int getBillingduration() {
        return billingduration;
    }

    public void setBillingduration(int billingduration) {
        this.billingduration = billingduration;
    }

    public long getCallanswertime() {
        return callanswertime;
    }

    public void setCallanswertime(long callanswertime) {
        this.callanswertime = callanswertime;
    }

    public long getCallbegintime() {
        return callbegintime;
    }

    public void setCallbegintime(Long callbegintime) {
        this.callbegintime = callbegintime;
    }

    public int getCalleeduration() {
        return calleeduration;
    }

    public void setCalleeduration(int calleeduration) {
        this.calleeduration = calleeduration;
    }

    public String getCalleeextennumber() {
        return calleeextennumber;
    }

    public void setCalleeextennumber(String calleeextennumber) {
        this.calleeextennumber = calleeextennumber;
    }

    public String getCalleenumber() {
        return calleenumber;
    }

    public void setCalleenumber(String calleenumber) {
        this.calleenumber = calleenumber;
    }

    public String getCalleephonenumber() {
        return calleephonenumber;
    }

    public void setCalleephonenumber(String calleephonenumber) {
        this.calleephonenumber = calleephonenumber;
    }

    public String getCalleerealnumber() {
        return calleerealnumber;
    }

    public void setCalleerealnumber(String calleerealnumber) {
        this.calleerealnumber = calleerealnumber;
    }

    public int getCallerduration() {
        return callerduration;
    }

    public void setCallerduration(int callerduration) {
        this.callerduration = callerduration;
    }

    public int getCallerlocationid() {
        return callerlocationid;
    }

    public void setCallerlocationid(int callerlocationid) {
        this.callerlocationid = callerlocationid;
    }

    public String getCallerlocationname() {
        return callerlocationname;
    }

    public void setCallerlocationname(String callerlocationname) {
        this.callerlocationname = callerlocationname;
    }

    public String getCallerphonenumber() {
        return callerphonenumber;
    }

    public void setCallerphonenumber(String callerphonenumber) {
        this.callerphonenumber = callerphonenumber;
    }

    public Date getCallextentime() {
        return callextentime;
    }

    public void setCallextentime(Date callextentime) {
        this.callextentime = callextentime;
    }

    public long getCalllogid() {
        return calllogid;
    }

    public void setCalllogid(long calllogid) {
        this.calllogid = calllogid;
    }

    public String getCallstatusname() {
        return callstatusname;
    }

    public void setCallstatusname(String callstatusname) {
        this.callstatusname = callstatusname;
    }

    public int getCarid() {
        return carid;
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }

    public int getDealerid() {
        return dealerid;
    }

    public void setDealerid(int dealerid) {
        this.dealerid = dealerid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMaterialid() {
        return materialid;
    }

    public void setMaterialid(int materialid) {
        this.materialid = materialid;
    }

    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public int getRecordingfileisshow() {
        return recordingfileisshow;
    }

    public void setRecordingfileisshow(int recordingfileisshow) {
        this.recordingfileisshow = recordingfileisshow;
    }

    public int getRecordingfiletype() {
        return recordingfiletype;
    }

    public void setRecordingfiletype(int recordingfiletype) {
        this.recordingfiletype = recordingfiletype;
    }

    public String getRecordingfileurl() {
        return recordingfileurl;
    }

    public void setRecordingfileurl(String recordingfileurl) {
        this.recordingfileurl = recordingfileurl;
    }

    public int getSerialid() {
        return serialid;
    }

    public void setSerialid(int serialid) {
        this.serialid = serialid;
    }

    public int getSourceid() {
        return sourceid;
    }

    public void setSourceid(int sourceid) {
        this.sourceid = sourceid;
    }

    public String getTracefileurl() {
        return tracefileurl;
    }

    public void setTracefileurl(String tracefileurl) {
        this.tracefileurl = tracefileurl;
    }

    public String getUsercookie() {
        return usercookie;
    }

    public void setUsercookie(String usercookie) {
        this.usercookie = usercookie;
    }

    public String getUserfield() {
        return userfield;
    }

    public void setUserfield(String userfield) {
        this.userfield = userfield;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserserviceid() {
        return userserviceid;
    }

    public void setUserserviceid(int userserviceid) {
        this.userserviceid = userserviceid;
    }

    public int getUserservicestatus() {
        return userservicestatus;
    }

    public void setUserservicestatus(int userservicestatus) {
        this.userservicestatus = userservicestatus;
    }

    public int getUsertypeid() {
        return usertypeid;
    }

    public void setUsertypeid(int usertypeid) {
        this.usertypeid = usertypeid;
    }


    public String getCallwaittimedesc() {
        if(this.callerduration!=0 &&  this.calleeduration!=0) {
            long time = this.callerduration - this.calleeduration;
            String ss = String.valueOf((time) % 60);
            String mm = String.valueOf((time) / 60 % 60);
            String hh = String.valueOf((time) / 60 / 60);
            return (hh.length() == 1 ? "0" + hh : hh) + ":" + (mm.length() == 1 ? "0" + mm : mm) + ":" + (ss.length() == 1 ? "0" + ss : ss);
        }
        return "00:00:00";
    }

    public void setCallwaittimedesc(String callwaittimedesc) {
        this.callwaittimedesc = callwaittimedesc;
    }

    public String getCalleedurationdescription() {
        String ss = String.valueOf((calleeduration/1000)%60);
        String mm = String.valueOf((calleeduration/1000)/60%60);
        String hh = String.valueOf((calleeduration/1000)/60/60);
        return  (hh.length()==1?"0"+hh:hh) +":"+ (mm.length()==1?"0"+mm:mm) +":"+ (ss.length()==1?"0"+ss:ss);
    }

    public void setCalleedurationdescription(String calleedurationdescription) {
        this.calleedurationdescription = calleedurationdescription;
    }

    public Broker getCallerBroker() {
        return callerBroker;
    }

    public void setCallerBroker(Broker callerBroker) {
        this.callerBroker = callerBroker;
    }

    public long getHangUpTime() {
        return this.calleeduration * 1000 + callanswertime;
        //return hangUpTime;
    }

    public void setHangUpTime(long hangUpTime) {
        this.hangUpTime = hangUpTime;
    }
}

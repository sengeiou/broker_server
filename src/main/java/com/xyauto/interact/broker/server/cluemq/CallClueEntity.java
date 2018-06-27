package com.xyauto.interact.broker.server.cluemq;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class CallClueEntity {
    
    /**
     * 来源id
     */
    private long id;
    /**
     * 话单表主键id
     */
    private long calllogid;
    /**
     * 主叫号码
     */
    private String callerphonenumber;
    /**
     * 被叫95号码
     */
    private String calleephonenumber;
    /**
     * 分机按键
     */
    private String calleeextennumber;
    /**
     * 被叫真实号码
     */
    private String calleerealnumber;
    /**
     * 被叫号码
     */
    private String calleenumber;
    
    /**
     * 呼叫开始时间
     */
    private Date callbegintime;
    
    private Date callextentime;
    
    /**
     * 通话结束时间
     */
    private Date callanswertime;
    
    /**
     * 呼叫秒数
     */
    private int callerduration;
    
    /**
     * 通话秒数
     */
    private int calleeduration;
    
    private int billingduration;
    
    /**
     * 服务表主键id
     */
    private int userserviceid;
    
    private int userservicestatus;
    
    /**
     * 经纪人或经销商ID
     */
    private int userid;

    /**
     * 客户类型 1：经纪人 4经销商
     */
    private int usertypeid;

    /**
     * 通话所带参数
     */
    private String userfield;

    /**
     * 响应日志
     */
    private String tracefileurl;

    /**
     * 通话录音地址
     */
    private String recordingfileurl;

    private int recordingfiletype;

    private int recordingfileisshow;

    private int callerlocationid;

    /**
     * 呼叫人号码归属地
     */
    private String callerlocationname;

    /**
     * 呼叫状态
     */
    private String callstatusname;

    /**
     * 车型ID
     */
    private int serialid;

    /**
     * 车款ID
     */
    private int carid;

    /**
     * 来源 1、汽车大全APP-IOS，2、M站，3、微店，4、汽车大全APP-android，5、汽车大全H5，6小程序
     */
    private int sourceid;

    /**
     * 用户cookie
     */
    private String usercookie;
    /**
     * 物料ID
     */
    private int materialid;

    /**
     * 渠道1
     */
    private String channelone;

    /**
     * 渠道2
     */
    private String channeltwo;

    /**
     * 新闻ID
     */
    private int newsid;

    /**
     * 新闻ID
     */
    private int dealerid;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the calllogid
     */
    public long getCalllogid() {
        return calllogid;
    }

    /**
     * @param calllogid the calllogid to set
     */
    public void setCalllogid(long calllogid) {
        this.calllogid = calllogid;
    }

    /**
     * @return the callerphonenumber
     */
    public String getCallerphonenumber() {
        return callerphonenumber;
    }

    /**
     * @param callerphonenumber the callerphonenumber to set
     */
    public void setCallerphonenumber(String callerphonenumber) {
        this.callerphonenumber = callerphonenumber;
    }

    /**
     * @return the calleephonenumber
     */
    public String getCalleephonenumber() {
        return calleephonenumber;
    }

    /**
     * @param calleephonenumber the calleephonenumber to set
     */
    public void setCalleephonenumber(String calleephonenumber) {
        this.calleephonenumber = calleephonenumber;
    }

    /**
     * @return the calleeextennumber
     */
    public String getCalleeextennumber() {
        return calleeextennumber;
    }

    /**
     * @param calleeextennumber the calleeextennumber to set
     */
    public void setCalleeextennumber(String calleeextennumber) {
        this.calleeextennumber = calleeextennumber;
    }

    /**
     * @return the calleerealnumber
     */
    public String getCalleerealnumber() {
        return calleerealnumber;
    }

    /**
     * @param calleerealnumber the calleerealnumber to set
     */
    public void setCalleerealnumber(String calleerealnumber) {
        this.calleerealnumber = calleerealnumber;
    }

    /**
     * @return the calleenumber
     */
    public String getCalleenumber() {
        return calleenumber;
    }

    /**
     * @param calleenumber the calleenumber to set
     */
    public void setCalleenumber(String calleenumber) {
        this.calleenumber = calleenumber;
    }

    /**
     * @return the callbegintime
     */
    public Date getCallbegintime() {
        return callbegintime;
    }

    /**
     * @param callbegintime the callbegintime to set
     */
    public void setCallbegintime(Date callbegintime) {
        this.callbegintime = callbegintime;
    }

    /**
     * @return the callextentime
     */
    public Date getCallextentime() {
        return callextentime;
    }

    /**
     * @param callextentime the callextentime to set
     */
    public void setCallextentime(Date callextentime) {
        this.callextentime = callextentime;
    }

    /**
     * @return the callanswertime
     */
    public Date getCallanswertime() {
        return callanswertime;
    }

    /**
     * @param callanswertime the callanswertime to set
     */
    public void setCallanswertime(Date callanswertime) {
        this.callanswertime = callanswertime;
    }

    /**
     * @return the callerduration
     */
    public int getCallerduration() {
        return callerduration;
    }

    /**
     * @param callerduration the callerduration to set
     */
    public void setCallerduration(int callerduration) {
        this.callerduration = callerduration;
    }

    /**
     * @return the calleeduration
     */
    public int getCalleeduration() {
        return calleeduration;
    }

    /**
     * @param calleeduration the calleeduration to set
     */
    public void setCalleeduration(int calleeduration) {
        this.calleeduration = calleeduration;
    }

    /**
     * @return the billingduration
     */
    public int getBillingduration() {
        return billingduration;
    }

    /**
     * @param billingduration the billingduration to set
     */
    public void setBillingduration(int billingduration) {
        this.billingduration = billingduration;
    }

    /**
     * @return the userserviceid
     */
    public int getUserserviceid() {
        return userserviceid;
    }

    /**
     * @param userserviceid the userserviceid to set
     */
    public void setUserserviceid(int userserviceid) {
        this.userserviceid = userserviceid;
    }

    /**
     * @return the userservicestatus
     */
    public int getUserservicestatus() {
        return userservicestatus;
    }

    /**
     * @param userservicestatus the userservicestatus to set
     */
    public void setUserservicestatus(int userservicestatus) {
        this.userservicestatus = userservicestatus;
    }

    /**
     * @return the userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     * @return the usertypeid
     */
    public int getUsertypeid() {
        return usertypeid;
    }

    /**
     * @param usertypeid the usertypeid to set
     */
    public void setUsertypeid(int usertypeid) {
        this.usertypeid = usertypeid;
    }

    /**
     * @return the userfield
     */
    public String getUserfield() {
        return userfield;
    }

    /**
     * @param userfield the userfield to set
     */
    public void setUserfield(String userfield) {
        this.userfield = userfield;
    }

    /**
     * @return the tracefileurl
     */
    public String getTracefileurl() {
        return tracefileurl;
    }

    /**
     * @param tracefileurl the tracefileurl to set
     */
    public void setTracefileurl(String tracefileurl) {
        this.tracefileurl = tracefileurl;
    }

    /**
     * @return the recordingfileurl
     */
    public String getRecordingfileurl() {
        return recordingfileurl;
    }

    /**
     * @param recordingfileurl the recordingfileurl to set
     */
    public void setRecordingfileurl(String recordingfileurl) {
        this.recordingfileurl = recordingfileurl;
    }

    /**
     * @return the recordingfiletype
     */
    public int getRecordingfiletype() {
        return recordingfiletype;
    }

    /**
     * @param recordingfiletype the recordingfiletype to set
     */
    public void setRecordingfiletype(int recordingfiletype) {
        this.recordingfiletype = recordingfiletype;
    }

    /**
     * @return the recordingfileisshow
     */
    public int getRecordingfileisshow() {
        return recordingfileisshow;
    }

    /**
     * @param recordingfileisshow the recordingfileisshow to set
     */
    public void setRecordingfileisshow(int recordingfileisshow) {
        this.recordingfileisshow = recordingfileisshow;
    }

    /**
     * @return the callerlocationid
     */
    public int getCallerlocationid() {
        return callerlocationid;
    }

    /**
     * @param callerlocationid the callerlocationid to set
     */
    public void setCallerlocationid(int callerlocationid) {
        this.callerlocationid = callerlocationid;
    }

    /**
     * @return the callerlocationname
     */
    public String getCallerlocationname() {
        return callerlocationname;
    }

    /**
     * @param callerlocationname the callerlocationname to set
     */
    public void setCallerlocationname(String callerlocationname) {
        this.callerlocationname = callerlocationname;
    }

    /**
     * @return the callstatusname
     */
    public String getCallstatusname() {
        return callstatusname;
    }

    /**
     * @param callstatusname the callstatusname to set
     */
    public void setCallstatusname(String callstatusname) {
        this.callstatusname = callstatusname;
    }

    /**
     * @return the serialid
     */
    public int getSerialid() {
        return serialid;
    }

    /**
     * @param serialid the serialid to set
     */
    public void setSerialid(int serialid) {
        this.serialid = serialid;
    }

    /**
     * @return the carid
     */
    public int getCarid() {
        return carid;
    }

    /**
     * @param carid the carid to set
     */
    public void setCarid(int carid) {
        this.carid = carid;
    }

    /**
     * @return the sourceid
     */
    public int getSourceid() {
        return sourceid;
    }

    /**
     * @param sourceid the sourceid to set
     */
    public void setSourceid(int sourceid) {
        this.sourceid = sourceid;
    }

    /**
     * @return the usercookie
     */
    public String getUsercookie() {
        return usercookie;
    }

    /**
     * @param usercookie the usercookie to set
     */
    public void setUsercookie(String usercookie) {
        this.usercookie = usercookie;
    }

    /**
     * @return the materialid
     */
    public int getMaterialid() {
        return materialid;
    }

    /**
     * @param materialid the materialid to set
     */
    public void setMaterialid(int materialid) {
        this.materialid = materialid;
    }

    /**
     * @return the channelone
     */
    public String getChannelone() {
        return channelone;
    }

    /**
     * @param channelone the channelone to set
     */
    public void setChannelone(String channelone) {
        this.channelone = channelone;
    }

    /**
     * @return the channeltwo
     */
    public String getChanneltwo() {
        return channeltwo;
    }

    /**
     * @param channeltwo the channeltwo to set
     */
    public void setChanneltwo(String channeltwo) {
        this.channeltwo = channeltwo;
    }

    /**
     * @return the newsid
     */
    public int getNewsid() {
        return newsid;
    }

    /**
     * @param newsid the newsid to set
     */
    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    /**
     * @return the dealerid
     */
    public int getDealerid() {
        return dealerid;
    }

    /**
     * @param dealerid the dealerid to set
     */
    public void setDealerid(int dealerid) {
        this.dealerid = dealerid;
    }
    
}

package com.xyauto.interact.broker.server.cluemq;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ClueMessageEntity {

    private Long createTime = new Date().getTime();
    private Object id;
    private String msg;
    private long incrementId;
    private transient Map<String,Object> containterParameters;
    private String parameters;

    public Long getCreateTime() {
        return createTime;
    }

    public ClueMessageEntity(Object id, String msg) {
        super();
        this.id = id;
        this.msg = msg;
        containterParameters =new HashMap<>();
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public void addParameter(String key,Object value){
        containterParameters.put(key,value);
    }

    public Map<String,Object> getContainterParameters(){
        return containterParameters;
    }

    public void setContainterParameters(Map<String, Object> containterParameters) {
        this.containterParameters = containterParameters;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the incrementId
     */
    public long getIncrementId() {
        return incrementId;
    }

    /**
     * @param incrementId the incrementId to set
     */
    public void setIncrementId(long incrementId) {
        this.incrementId = incrementId;
    }

}


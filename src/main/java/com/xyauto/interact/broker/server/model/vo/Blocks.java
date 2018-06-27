package com.xyauto.interact.broker.server.model.vo;

import java.util.Date;

/**
 * Created by shiqm on 2018-03-13.
 */
public class Blocks {

    /**
     * 块id
     */
    private int id;

    /**
     * 块名称
     */
    private String name;

    /**
     * 块内容
     */
    private Object content;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 是否已删除
     */
    private short is_deleted;


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the content
     */
    public Object getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(Object content) {
        this.content = content;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public short getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(short is_deleted) {
        this.is_deleted = is_deleted;
    }
}

package com.chzu.txgc.pdd.Bean;

public class EventMsgBean {

    private int code;
    private Object obj;

    public EventMsgBean() {
    }

    public EventMsgBean(int code) {
        this.code = code;
    }

    public EventMsgBean(int code, Object obj) {
        this.code = code;
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}

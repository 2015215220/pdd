package com.chzu.txgc.pdd.Bean;

import java.io.Serializable;

public class ChildinitBean implements Serializable {//Serializable  序列化数据传输需要
    private String name;
    private int imageId;
    private String price;
    private String fk;

    public String getPri() {
        return pri;
    }

    public void setPri(String pri) {
        this.pri = pri;
    }

    private String pri;

    public String getFk() {
        return fk;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }

    public ChildinitBean(String name, int imageId, String price, String fk) {
        this.name = name;
        this.imageId = imageId;
        this.price = price;
        this.fk = fk;
    }

    public ChildinitBean(String name, int imageId, String price, String fk, String pri) {
        this.name = name;
        this.imageId = imageId;
        this.price = price;
        this.fk = fk;
        this.pri = pri;
    }

    public ChildinitBean(String name, int imageId, String price) {
        this.name = name;
        this.imageId = imageId;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ChildinitBean(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

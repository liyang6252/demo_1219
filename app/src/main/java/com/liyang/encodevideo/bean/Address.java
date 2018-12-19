package com.liyang.encodevideo.bean;

public class Address {
    private String address;
    private boolean useful;
    public Address(String address,boolean useful){
        super();
        this.address = address;
        this.useful = useful;
    }
    public boolean isUseful() {
        return useful;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUseful(boolean useful) {
        this.useful = useful;
    }
}

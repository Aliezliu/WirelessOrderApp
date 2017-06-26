package com.softeem.orderapp.bean;

import java.io.Serializable;

public class MenuBean implements Serializable {
    private int id;
    private String address;
    private String introduce;
    private String sortId;
    private double price;
    private double discount;
    private boolean isDiscount;
    private boolean isRecommend;
    private boolean isOrder;
    public int time;
    public String createDate;
    private String name;
    public String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isDiscount() {
        return isDiscount;
    }

    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MenuBean [id=" + id + ", address=" + address + ", introduce=" + introduce
                + ", sortId=" + sortId + ", price=" + price + ", discount=" + discount
                + ", isDiscount=" + isDiscount + ", isRecommend=" + isRecommend + ", isOrder="
                + isOrder + ", time=" + time + ", createDate=" + createDate + ", name="
                + name + ", url=" + url  + "]";
    }


}

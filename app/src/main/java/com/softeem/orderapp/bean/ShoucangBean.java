package com.softeem.orderapp.bean;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ShoucangBean {
    public int id;
    public int menuId;
    public String name;
    public double price;
    //public String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}

package com.softeem.orderapp.bean;

/**
 * Created by Edward on 2017/6/22.
 */

public class OrderItemBean {
    public String state = "未下单";
    public int count;

    public double itemTotalPrice;
    public double itemCutPrice;

    public MenuBean menuBean;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(double itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }

    public double getItemCutPrice() {
        return itemCutPrice;
    }

    public void setItemCutPrice(double itemCutPrice) {
        this.itemCutPrice = itemCutPrice;
    }

    public MenuBean getMenuBean() {
        return menuBean;
    }

    public void setMenuBean(MenuBean menuBean) {
        this.menuBean = menuBean;
    }
}

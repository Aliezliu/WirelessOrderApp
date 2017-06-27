package com.softeem.orderapp.bean;

/**
 * Created by Aliez on 2017/6/26.
 */

public class OrderItemBeanForJson {
    public String state = "未下单";
    public int count;
    public double itemTotalPrice;
    public double itemCutPrice;
    public int menuId;
    public OrderItemBeanForJson(OrderItemBean oib) {
        this.state = oib.getState();
        this.count = oib.getCount();
        this.itemTotalPrice = oib.getItemTotalPrice();
        this.itemCutPrice = oib.getItemCutPrice();
        this.menuId = oib.menuBean.getId();
    }
}
